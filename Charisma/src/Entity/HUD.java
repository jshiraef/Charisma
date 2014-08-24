package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HUD {

	private Player player;
	
	private BufferedImage image;
	private Font font;
	
	public HUD(Player p) {
		player = p;
		
		try {
			image = ImageIO.read(getClass(). getResourceAsStream("/HUD/hud2.png"));
			
			font = new Font("Arial", Font.PLAIN, 16);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image,  0,  20, null);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 35);
		g.drawString(player.getCharisma() / 100 + "/" + player.getMaxCharisma()/ 100, 30, 55);
	}
	
}
