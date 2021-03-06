package edu.usf.eng.pie.avatars4change.avatar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Sprite {
	String TAG = "avatar.Sprite";
	
	String name = "UNNAMED";
	String filename = null;
	Location location = new Location();
	
//	Bitmap image;
	
	//constructor
	public Sprite(String newName, String fileName, Location newLocation){
		set(newName,fileName,newLocation);
	}
	
	public void set (String newName, String fileName, Location newLocation){
		name = newName;
		this.filename = fileName;
		location    = newLocation;
	}
	
	public void setLocation(Location newLoc){
		location = newLoc;
	}

	public Bitmap loadImage(String fName){
		//image = BitmapFactory.decodeFile(fileDir+currentFrame+".png");
		BitmapFactory.Options options = new BitmapFactory.Options(); options.inPurgeable = true;
		Bitmap image = BitmapFactory.decodeFile(fName,options);
		if(image==null)	Log.e("sprite","file " + fName + " failed to load!");
		//log.d(TAG,"sprite file '"+fName+"' loaded");
		return image;
	}
	
	//draws the sprite on given canvas c at object location relative to given location L
	public void draw(Canvas c){
		Bitmap image = loadImage(this.filename);
		c.save();
		if(image == null){
			if(name == null){
				Log.v("sprite","no image to draw here; move along...");
			} else {
				Log.e("sprite","cannot draw sprite "+name+", no image!");
			}
			return;	//don't draw if no image or no name
		}	//implied else
		// assume given size is size of largest edge
		Rect source;
		source = new Rect(0, 0, image.getWidth(), image.getHeight());//TODO to use sprite sheet, adjust this to select part of image
		int w = 0,h = 0;	//image width & height (actually radius of image)
		if(image.getWidth()>image.getHeight()){ 
			w = location.size;
			h = Math.round( (float)location.size * ((float)image.getHeight()/(float)image.getWidth()) );
		} else {
			h = location.size;
			w = Math.round( (float)location.size * ((float)image.getWidth()/(float)image.getHeight()) );
		}
		/*
		dest = new Rect(L.x-w/2, L.y-h/2, L.x+w/2, L.y+h/2);
		//Log.d("sprite","w=" + Integer.toString(w) + " h=" + Integer.toString(h));
		 */
		c.translate(location.x, -location.y);
		c.rotate(location.rotation);
		Rect dest = new Rect(-w/2,-h/2,w/2,h/2);
		c.drawBitmap(image, source, dest, null);
		c.restore();
		; //log.d(TAG,w+"x"+h+" sprite drawn at "+location.x+","+location.y);
	}
}
