import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 */ 

//Singeton pattern
public class Controller implements KeyListener {
        
	private static boolean KeyQPressed = false;
	private static boolean KeySPressed = false;
	private static boolean KeyDPressed = false;
	private static boolean KeyWPressed = false;
	private static boolean KeyFPressed = false;

	private static boolean KeyUpPressed = false;
	private static boolean KeyDownPressed = false;
	private static boolean KeyLeftPressed = false;
	private static boolean KeyRightPressed = false;
	private static boolean KeyEnterPressed = false;

	private static final Controller instance = new Controller();
	   
	public Controller() {}
	 
	public static Controller getInstance(){
	        return instance;
	    }
	   
	@Override
	// Key pressed , will keep triggering 
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) 
	{ 
		switch (e.getKeyCode())
		{
//			case KeyEvent.VK_Q: setKeyQPressed(true); break;
//			case KeyEvent.VK_S: setKeySPressed(true); break;
//			case KeyEvent.VK_W: setKeyWPressed(true); break;
//			case KeyEvent.VK_D: setKeyDPressed(true); break;
			case KeyEvent.VK_Q: setKeyQPressed(true); break;
			case KeyEvent.VK_W: setKeySPressed(true); break; //bottom
			case KeyEvent.VK_Z: setKeyWPressed(true); break; //top
			case KeyEvent.VK_D: setKeyDPressed(true); break;
			case KeyEvent.VK_F: setKeyFPressed(true); break;
			case KeyEvent.VK_UP: setKeyUpPressed(true); break;
			case KeyEvent.VK_DOWN:setKeyDownPressed(true); break;
			case KeyEvent.VK_RIGHT:setKeyRightPressed(true); break;
			case KeyEvent.VK_LEFT: setKeyLeftPressed(true); break;
			case KeyEvent.VK_ENTER: setKeyEnterPressed(true); break;
		    default: break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{ 
		switch (e.getKeyCode())
		{
//			case KeyEvent.VK_Q: setKeyQPressed(false); break;
//			case KeyEvent.VK_S: setKeySPressed(false); break;
//			case KeyEvent.VK_W: setKeyWPressed(false); break;
//			case KeyEvent.VK_D: setKeyDPressed(false); break;
			case KeyEvent.VK_Q: setKeyQPressed(false); break;
			case KeyEvent.VK_W: setKeySPressed(false); break; //bottom
			case KeyEvent.VK_Z: setKeyWPressed(false); break; //top
			case KeyEvent.VK_D: setKeyDPressed(false); break;
			case KeyEvent.VK_F: setKeyFPressed(false); break;
			case KeyEvent.VK_UP: setKeyUpPressed(false); break;
			case KeyEvent.VK_DOWN: setKeyDownPressed(false); break;
			case KeyEvent.VK_RIGHT: setKeyRightPressed(false); break;
			case KeyEvent.VK_LEFT: setKeyLeftPressed(false); break;
			case KeyEvent.VK_ENTER: setKeyEnterPressed(false); break;
		    default: break;
		}

	}

	public boolean isKeyQPressed() {
		return KeyQPressed;
	}
	public boolean isKeySPressed() {
		return KeySPressed;
	}
	public boolean isKeyDPressed() {
		return KeyDPressed;
	}
	public boolean isKeyWPressed() { return KeyWPressed; }
	public boolean isKeyFPressed() { return KeyFPressed; }

	public boolean isKeyUpPressed() { return KeyUpPressed; }
	public boolean isKeyDownPressed() { return KeyDownPressed; }
	public boolean isKeyLeftPressed() { return KeyLeftPressed; }
	public boolean isKeyRightPressed() { return KeyRightPressed; }
	public boolean isKeyEnterPressed() { return KeyEnterPressed; }

	public void setKeyQPressed(boolean keyQPressed) { KeyQPressed = keyQPressed; }
	public void setKeySPressed(boolean keySPressed) {
		KeySPressed = keySPressed;
	}
	public void setKeyDPressed(boolean keyDPressed) {
		KeyDPressed = keyDPressed;
	}
	public void setKeyWPressed(boolean keyWPressed) {
		KeyWPressed = keyWPressed;
	}
	public void setKeyFPressed(boolean keyFPressed) { KeyFPressed = keyFPressed; }

	public void setKeyUpPressed(boolean keyUpPressed) { KeyUpPressed = keyUpPressed;}
	public void setKeyDownPressed(boolean keyDownPressed) { KeyDownPressed = keyDownPressed;}
	public void setKeyRightPressed(boolean keyRightPressed) { KeyRightPressed = keyRightPressed;}
	public void setKeyLeftPressed(boolean keyLeftPressed) { KeyLeftPressed = keyLeftPressed;}
	public void setKeyEnterPressed(boolean keyEnterPressed) { KeyEnterPressed = keyEnterPressed; }

}

/*
 * 
 * KEYBOARD :-) . can you add a mouse or a gamepad 

 *@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ @@@@@@@@@@@@@@@

  @@@     @@@@    @@@@    @@@@    @@@@     @@@     @@@     @@@     @@@     @@@  

  @@@     @@@     @@@     @@@@     @@@     @@@     @@@     @@@     @@@     @@@  

  @@@     @@@     @@@     @@@@    @@@@     @@@     @@@     @@@     @@@     @@@  

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@     @@@     @@@     @@@      @@      @@@     @@@     @@@     @@@     @@@     @

@     @@@   W   @@@     @@@      @@      @@@     @@@     @@@     @@@     @@@     @

@@    @@@@     @@@@    @@@@    @@@@    @@@@     @@@     @@@     @@@     @@@     @

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@N@@@@@@@@@@@@@@@@@@@@@@@@@@@

@@@     @@@      @@      @@      @@      @@@     @@@     @@@     @@@     @@@    

@@@   A   @@@  S     @@  D     @@      @@@     @@@     @@@     @@@     @@@     @@@    

@@@@ @  @@@@@@@@@@@@ @@@@@@@    @@@@@@@@@@@@    @@@@@@@@@@@@     @@@@   @@@@@   

    @@@     @@@@    @@@@    @@@@    $@@@     @@@     @@@     @@@     @@@     @@@

    @@@ $   @@@      @@      @@ /Q   @@ ]M   @@@     @@@     @@@     @@@     @@@

    @@@     @@@      @@      @@      @@      @@@     @@@     @@@     @@@     @@@

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@       @@@                                                @@@       @@@       @

@       @@@              SPACE KEY       @@@        @@ PQ     

@       @@@                                                @@@        @@        

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * 
 * 
 * 
 * 
 * 
 */
