package demo.wrappers;

import org.openqa.selenium.WebElement;

public class Wrappers {

    public void sendkeys(WebElement ele, String s){
        ele.sendKeys(s);
    }

    public void clk(WebElement ele){
        ele.click();
    }
  
}
