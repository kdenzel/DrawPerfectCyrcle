package de.kswmd.drawperfectcycle;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 *
 * @author kai
 */
public class DrawPerfectCyrcle {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://neal.fun/perfect-circle/");
        driver.manage().window().maximize();
        long ts;
        Scanner sc = new Scanner(System.in);
        System.out.println("Press enter to go on");
        sc.nextLine();
        //ts = System.currentTimeMillis();
        //while(ts+10000 > System.currentTimeMillis());
        try {

            Point windowAbsolutePosition = driver.manage().window().getPosition();
            JavascriptExecutor selenium_driver = (JavascriptExecutor) driver;
            Long offset_x_viewport = (Long) selenium_driver.executeScript("return window.screenX + (window.outerWidth - window.innerWidth) / 2 - window.scrollX;");
            Long offset_y_viewport = (Long) selenium_driver.executeScript("return window.screenY + (window.outerHeight - window.innerHeight) - window.scrollY;");

            Point offset = new Point(offset_x_viewport.intValue(), offset_y_viewport.intValue());

            Robot robot = new Robot();
            System.out.println("Move mouse to " + offset);
            robot.mouseMove(offset.x, offset.y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            //WebElement button = driver.findElement(By.xpath("//button[@data-v-f2039242 and @class='on']"));
            //button.click();

            WebElement viewBox = driver.findElement(By.xpath("//*[local-name()='svg' and @data-v-f2039242]"));
            Point viewBoxLocation = viewBox.getLocation();

            //WebElement span = driver.findElements(By.xpath("//span[@data-v-f2039242]")).get(3);
            Point middleOfCircle = new Point(offset.x + 1 + viewBoxLocation.x + viewBox.getRect().width / 2,
                    offset.y + viewBoxLocation.y + viewBox.getRect().height / 2
            );
            System.out.println("Move mouse to " + middleOfCircle);
            robot.mouseMove(middleOfCircle.x, middleOfCircle.y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            ts = System.currentTimeMillis();
            while (ts + 1000 > System.currentTimeMillis());
            int radiusInPx = 500;
            double radian = Math.PI / 180;
            for (int i = 0; i <= 370; i++) {
                double radians = i % 360 * radian;
                Double newX = radiusInPx * Math.cos(radians) + middleOfCircle.x;
                Double newY = radiusInPx * Math.sin(radians) + middleOfCircle.y;
                Point np = new Point(newX.intValue(), newY.intValue());
                System.out.println("Move mouse to " + np);
                robot.mouseMove(np.x, np.y);
                ts = System.currentTimeMillis();
                while (ts + 10 > System.currentTimeMillis());
                if (i == 0) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                }
            }
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Press enter to exit");
        sc.nextLine();
        driver.close();
    }
}
