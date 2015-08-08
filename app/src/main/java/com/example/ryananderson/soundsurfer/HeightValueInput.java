import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import json.JSONArray;
import json.JSONObject;

// HeightValueInput is its own separate applet meant to record height values for a given song.
public class HeightValueInput extends Applet implements MouseMotionListener, KeyListener{

	public static Dimension dim;
	private Graphics bufferGraphics;
	private Image offscreen;

	boolean started = false;
	private String songPath = Main.RESOURCES_DIR+"/demons.wav";
	//blue.wav
	private int mouseY;
	//private int[] heightVals;
	private ArrayList<Integer> heightVals = new ArrayList<Integer>();

	private AudioClip song;
	
	@Override
	public void init()
	{

		setSize(Main.APPLET_WIDTH, Main.APPLET_HEIGHT);
		dim = getSize();
		setFocusable(true);
		requestFocusInWindow();
		offscreen = createImage(dim.width, dim.height); 
		bufferGraphics = offscreen.getGraphics();
		song = getAudioClip(getDocumentBase(), songPath);
		
		addKeyListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void start()
	{
		//System.out.println("Height value input start method executed.");
		new Thread(new Runnable(){
			public void run()
			{
				while(true)
				{
					repaint();
					Main.pause(Main.BETWEEN_FRAME_WAIT_TIME);
				}
			}
		}).start();
	}



	@Override
	public void paint(Graphics g)
	{
		//System.out.println("paint executed");
		bufferGraphics.clearRect(0, 0, dim.width, dim.height); 
		draw(bufferGraphics);
		g.drawImage(offscreen, 0, 0, this);
	}

	@Override
	public void update(Graphics g)
	{
		// Code to update here
		if(started)
		{
			heightVals.add(WaveManager.DEFAULT_PIXEL_Y-mouseY);
			//System.out.println("New height val added");
		}

		paint(g);
	}

	private void draw(Graphics g)
	{
		// Code for drawing here

		// Color background if started
		if(started)
		{
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, Main.APPLET_WIDTH, Main.APPLET_HEIGHT);
		}
		
		//Draw line to show default pixelY
		g.setColor(Color.blue);
		g.fillRect(0, WaveManager.DEFAULT_PIXEL_Y, Main.APPLET_WIDTH, SoundWavePixel.DEFAULT_HEIGHT);

		//Draw black circle at mouseY
		g.setColor(Color.BLACK);
		g.fillOval(280, mouseY-20, 40, 40);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			//System.out.println("<Space> pressed");
			song.play();
			started = true;
		}
		else if(e.getKeyCode()==KeyEvent.VK_E)
		{
			started = false;
			song.stop();
			// Save heightValues to a JSON
			// Contruct the JSON based on what's already in songValues.txt

			String whatWeAlreadyHave = "";
			String songName = songPath.split("/")[songPath.split("/").length-1];
			try {
				File file = new File("songValues.txt");
				FileInputStream fis;
				fis = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fis.read(data);
				fis.close();
				whatWeAlreadyHave = new String(data, "UTF-8");
				System.out.println("Currently in songValues is "+whatWeAlreadyHave);

				JSONObject obj = new JSONObject(whatWeAlreadyHave);
				JSONArray array = new JSONArray(heightVals.toArray());
				obj.append(songName, array);

				//Finally, save the toString of the revised JSONObject back to the file.
				FileWriter fw = new FileWriter(file);
				System.out.println("About to write "+obj.toString()+" to songValues.txt");
				fw.write(obj.toString());
				fw.flush();
				fw.close();
				
			} catch(FileNotFoundException fnfe) {
				System.out.println("songValues.txt wasn't found");
				fnfe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}



			// Print out the height values to the console.
			String str = "";
			for(int v: heightVals) str += v+", ";
			System.out.println("Final heightVals: "+str);
		}
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	public void mouseDragged(MouseEvent arg0) {}

	public void mouseMoved(MouseEvent e) {
		//System.out.println("Mouse Moved");
		mouseY = e.getY();
	}


}
