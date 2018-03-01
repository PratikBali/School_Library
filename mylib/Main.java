import java.awt.*;
import javax.swing.*;

public class Main implements Runnable
{
	final Frame frame;

	public Main(Frame frame)
	{
		this.frame = frame;
	}

	public void run()
	{
		frame.show();
	}

	public static void main(String[] args)
	{
		JDialog.setDefaultLookAndFeelDecorated(true);
		// Throw a nice little title page up on the screen first
		new Splash().showSplash(1000);
		EventQueue.invokeLater(new Main(new JL03()));
	}
}