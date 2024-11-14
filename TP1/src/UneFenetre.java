import java.awt.*;
import javax.swing.*;

class UneFenetre extends JFrame
{
    UnMobile Mobile1;
    UnMobile Mobile2;
    UnMobile Mobile3;
    UnMobile Mobile4;
    private final int LARG=400, HAUT=25;
    
    public UneFenetre()
    {
        super("le mobile");
        Container leContainer = getContentPane();
        leContainer.setLayout(new GridLayout(4,1));

        Mobile1 = new UnMobile(LARG,HAUT);
        Mobile2 = new UnMobile(LARG,HAUT);
        Mobile3 = new UnMobile(LARG,HAUT);
        Mobile4 = new UnMobile(LARG,HAUT);

        leContainer.add(Mobile1);
        leContainer.add(Mobile2);
        leContainer.add(Mobile3);
        leContainer.add(Mobile4);

        Thread laTache1 = new Thread(Mobile1);
        Thread laTache2 = new Thread(Mobile2);
        Thread laTache3 = new Thread(Mobile3);
        Thread laTache4 = new Thread(Mobile4);

        laTache1.start();
        laTache2.start();
        laTache3.start();
        laTache4.start();

        setSize(1280,720);
        setVisible(true);
    }
}
