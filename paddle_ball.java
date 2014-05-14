float ballX = random(200);
float ballY = random(200);
int radius = 10;

int paddleWidth = 100;
int paddleHeight = 10;
int paddleY;

float xVelocity = random(4, 6);
float yVelocity = random(2, 3);

void setup(){
 size(400, 400);
 paddleY = height - 70;
}

void draw(){
  background(100, 0, 130);
  fill(0, 200, 200);
  ellipse(ballX, ballY, radius*2, radius*2);
  rect(mouseX, paddleY, paddleWidth, paddleHeight);
  isGameOver();
  moveBall();
  isChangeVelocity();
}

void moveBall(){
  ballX += xVelocity;
  ballY += yVelocity;
}

void isChangeVelocity(){
  if (hitsLeftWall()){
    xVelocityChange();
  }
  else if (hitsRightWall()){
    xVelocityChange();
  }

  if (hitsPaddle()){
    yVelocityChange();
  }
  else if (hitsCeiling()){
    yVelocityChange();
  }
}

void isGameOver(){
  if (ballY > height){
    noLoop();}
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

float ballLeft(){
  return ballX - radius;
}

float ballBottom(){
  return ballY + radius;
}
