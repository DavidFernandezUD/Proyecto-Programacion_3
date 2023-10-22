package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Sign extends SuperObject {

    public OBJ_Sign() {

        name = "Sign";

        try{
            image = ImageIO.read(getClass().getResourceAsStream("..\\res\\objects\\sign.png"));
        }catch(IOException  e) {
            e.printStackTrace();
        }
    }
}