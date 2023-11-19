package main.objects;

import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class OBJ_Sign extends SuperObject {

    public OBJ_Sign() {

        name = "Sign";

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("..//main.res//objects//sign.png")));
        }catch(IOException  e) {
            e.printStackTrace();
        }
    }
}