/*
 * turtle graphics example to illustrate roundoff error
 * 
 * when calculating the path with int coordinates
 * the roundoff means that the forward and back paths do not
 * match (this looks even worse when the back paths are
 * drawn as well)
 */

import terrapin.*;

Terrapin t;

int h;

void setup() {
  // create a blank window to draw on
  size(800,800);
  background(0);

  h=height;

  // create Terrapin
  t = new Terrapin(this);

  // set the Terrapin's pen color to an r,g,b value
  t.setPenColor(0, 255, 0);

  t.up();
  t.left(90);
  t.backward(height/2);
  t.down();
  noLoop();
}

void line(float len) {
  if(len<=1) {
    t.forward(len);
    t.backward(len);
  } else {
    t.forward(len/2);
    t.right(30);
    line(len/1.5);
    t.left(80);
    line(len/2);
    t.right(50);
    t.backward(len/2);
  }
}

void draw() {
  line(h*.7);
}

