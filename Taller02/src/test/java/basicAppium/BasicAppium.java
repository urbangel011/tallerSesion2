package basicAppium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class BasicAppium {

    private AppiumDriver driver;

    private By nomNotaObtenida=By.id("com.vrproductiveapps.whendo:id/home_list_item_text");
    private String nomTitulo="Titulo3";
    private String nomNota="Nota3";
    @BeforeEach
    void before() throws MalformedURLException {
        //ingresar capabilities del json generado en appium inspector
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:deviceName", "SM-G975F");
        capabilities.setCapability("appium:platformVersion", "11");
        capabilities.setCapability("appium:appPackage", "com.vrproductiveapps.whendo");
        capabilities.setCapability("appium:appActivity", "com.vrproductiveapps.whendo.ui.HomeActivity");
        capabilities.setCapability("platformName", "Android");
        //poner la ruta del appium server, lo puedes sacar del inspector
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        // implicit wait
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        System.out.println("Ejecucion iniciada");
    }

    @AfterEach
    void after() throws InterruptedException {
        Thread.sleep(4000);
        driver.quit();
        System.out.println("Ejecución finalizada");
    }

    @Test
    void verifyNameNote() {
        System.out.println("TEST INCIADO");

        //Boton + para añadir
        driver.findElement(By.id("com.vrproductiveapps.whendo:id/fab")).click();
        //Escribir titulo
        driver.findElement(By.xpath("//android.widget.EditText[@resource-id='com.vrproductiveapps.whendo:id/noteTextTitle']")).sendKeys(nomTitulo);
        //Escribir nombre de la nota
        driver.findElement(By.xpath("//android.widget.EditText[@resource-id='com.vrproductiveapps.whendo:id/noteTextNotes']")).sendKeys(nomNota);
        //pulsar en check para guardar
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.vrproductiveapps.whendo:id/saveItem']")).click();

        //explicit wait
        WebDriverWait explicitWait = new WebDriverWait(driver,15);
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(nomNotaObtenida));

        //VERIFICATION RESULT

        String resultadoObtenido=driver.findElement(nomNotaObtenida).getText();
        String resultadoEsperado ="Titulo3";

        Assertions.assertEquals(resultadoEsperado,resultadoObtenido,"La suma fue incorrecta");
        System.out.println("Resultado Obtenido: "+resultadoObtenido);
    }

}
