import terrapin.*;

Terrapin t;

int h;

// create a blank window to draw on
size(800,800);
background(0);

h=height;

// create Terrapin
t = new Terrapin(this);

// set the Terrapin's pen color to an r,g,b value
t.setPenColor(0, 255, 0);

print(t);
for(int i=0;i<7;i++) {
  t.forward(100);
  t.right(360.0/7);
}
print(t);

