float ballX = random(200);
float ballY = random(200);
int radius = 10;
boolean ballMoving = false;

ArrayList bricks;

int paddleWidth = 100;
int paddleHeight = 10;
int paddleY;

int score = 0;

float xVelocity = random(4, 6);
float yVelocity = random(2, 3);

void setup(){
 size(400, 400);
 paddleY = height - 70;
 bricks = new ArrayList();

 for (int i = 1; i < 7; i ++){
   Brick brick = new Brick();
   brick.setLocation(50 * i, 50);
   bricks.add(brick);
  }
}

void draw(){
  background(0, 108, 135);

  fill(255, 234, 0);
  ellipse(ballX, ballY, radius*2, radius*2);

  fill(240, 126, 65);
  rect(mouseX, paddleY, paddleWidth, paddleHeight);

  for (int i = 0; i < bricks.size(); i ++){
    Brick b = (Brick) bricks.get(i);
    b.brickDisplay();
  }

  fill(255, 0, 0);
  textSize(30);
  text(score, 75, 150);

  isGameOver();
  applyVelocity();
  changeVelocity();
}


void mouseClicked(){
  if (ballMoving){
    float xVelocity = random(4, 6);
    float yVelocity = random(2, 3);
    ballMoving = true;
   }
};

void applyVelocity(){
  ballX += xVelocity;
  ballY += yVelocity;
}

void changeVelocity(){
  if (hitsLeftWall()){
    xVelocityChange();
  }
  else if (hitsRightWall()){
    xVelocityChange();
  }

  if (hitsPaddle()){
    yVelocityChange();
    incrementScore();
  }
  else if (hitsCeiling()){
    yVelocityChange();
  }
}

void isGameOver(){
  if (ballY > height){
    noLoop();}
}

void incrementScore(){
  score += 1;
}

boolean hitsPaddle(){
  boolean result = false;
  if ((abovePaddle()) && (paddleSurface())){
  result = true;
  }
  return result;
}

boolean hitsCeiling(){
  boolean result = false;
  if (ballTop() <= height-height){
  result = true;
  }
  return result;
}

boolean hitsRightWall(){
  boolean result = false;
  if (ballRight() >= width){
    result = true;
  }
  return result;
}

boolean hitsLeftWall(){
  boolean result = false;
  if (ballLeft() <= width-width){
  result = true;
  }
  return result;
}

boolean abovePaddle(){
  boolean result = false;
  if ((ballBottom() >= paddleY) && (ballTop() <= paddleY + paddleHeight)){
  result = true;
  }
  return result;
}

boolean paddleSurface(){
  boolean result = false;
   if ((ballX >= mouseX) && (ballX <= mouseX + paddleWidth)){
   result = true;
   }
   return result;
}

void xVelocityChange(){
  xVelocity = xVelocity * -1;
}

void yVelocityChange(){
  yVelocity = yVelocity * -1;
}

float ballTop(){
  return ballY - radius;
}

float ballRight(){
  return ballX + radius;
}

float ballBottom(){
  return ballY + radius;
}

float ballLeft(){
  return ballX - radius;
}

class Brick{
  float x, y;

  void setLocation(float x, float y){
    this.x = x;
    this.y = y;
  }

  void brickDisplay(){
    float brickWidth = 50, brickHeight = 25;

    fill(255, 0, 255);
    rect(this.x, this.y, brickWidth, brickHeight);
  }

}
