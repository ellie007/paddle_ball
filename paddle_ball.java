int paddleWidth = 100;
int paddleHeight = 10;
float paddleX = 150;
int paddleY;

int radius = 8;
float ballX = paddleX;
float ballY = paddleY;
boolean gameInProgress = false;

ArrayList<Brick> bricks;
int brickY = 50;
float brickWidth = 50, brickHeight = 25;
int numOfBricks = 7;

int score = 0;

float xVelocity = random(4, 6);
float yVelocity = random(-4, -3);

void setup() {
  size(400, 400);
  paddleY = height - 70;
  bricks = new ArrayList();
  createBricks();
}

void createBricks() {
  for (int i = 1; i < numOfBricks; i ++) {
    Brick brick = new Brick();
    brick.setLocation(50 * i, brickY, brickWidth, brickHeight);
    bricks.add(brick);
  }
}

void draw() {
  background(0, 108, 135);

  fill(255, 234, 0);
  if (gameInProgress) {
    applyVelocity();
    changeVelocity();
    isGameOver();
  }
  else {
    ballStartEndPoint();
  }
  ellipse(ballX, ballY, radius*2, radius*2);

  fill(240, 126, 65);
  rect(paddleX, paddleY, paddleWidth, paddleHeight);

  displayGameBricks();

  fill(255, 0, 0);
  textSize(30);
  text(score, 75, 150);
}

void ballStartEndPoint() {
  ballX = paddleX + paddleWidth/2;
  ballY = paddleY - radius;
}

void displayGameBricks() {
  for (int i = 0; i < bricks.size(); i ++) {
    Brick b = (Brick) bricks.get(i);
    b.brickDisplay();
  }
}

void keyPressed() {
int paddleShiftingSpeed = 45;
  if (key == CODED) {
    if (keyCode == LEFT) {
      if (remainingPaddleDistanceLeft() < paddleWidth) {
        paddleX -= remainingPaddleDistanceLeft();
      }
      else if (withinGridCheckLeft()) {
        paddleX -= paddleShiftingSpeed;
      }
    }
    else if (keyCode == RIGHT) {
      if (remainingPaddleXDistanceRight() < paddleWidth) {
        paddleX += remainingPaddleXDistanceRight();
      }
      else if (withinGridCheckRight()) {
        paddleX += paddleShiftingSpeed;
      }
    }
    else if (keyCode == UP) {
      gameInProgress = true;
      score = 0;
    }
    else if (keyCode == DOWN) {
      gameInProgress = true;
      score =0;
    }
  }
}
float remainingPaddleDistanceLeft() {
  float result = paddleX - (width - width);
  return result;
}

float remainingPaddleXDistanceRight() {
  float result = width - (paddleX + paddleWidth);
  return result;
}

boolean withinGridCheckLeft() {
  boolean result = false;
  if (paddleX > width - width) {
    result = true;
  }
  return result;
}

boolean withinGridCheckRight(){
  boolean result = false;
  if (paddleX + paddleWidth < width) {
  result = true;
  }
  return result;
}

void mouseMoved() {
  if (mouseX > width - width && mouseX + paddleWidth < width) {
    paddleX = mouseX;
  }
}

void applyVelocity() {
  ballX += xVelocity;
  ballY += yVelocity;
}

void changeVelocity() {
  if (hitsLeftWall()) {
    xVelocityChange();
  }
  else if (hitsRightWall()) {
    xVelocityChange();
  }

  if (hitsPaddle()) {
    yVelocityChange();
    incrementScore();
  }
  else if (hitsCeiling()) {
    yVelocityChange();
  }

  if (hitsBricks()) {
    yVelocityChange();
    score += 5;
    numOfBricks -= 1;
    regenerateBricksCheck();
  }
}

void regenerateBricksCheck() {
  if (numOfBricks == 1){
    numOfBricks = 7;
    createBricks();
  }
}

void isGameOver() {
  if (ballY > height) {
    gameInProgress = false;
    resetGame();
  }
}

void resetGame() {
  xVelocity = random(4, 6);
  yVelocity = random(-4, -3);

  numOfBricks = 7;
  createBricks();
}

void incrementScore() {
  score += 1;
}

boolean hitsPaddle() {
  boolean result = false;
  if ( (abovePaddle()) && (paddleSurface()) ) {
  result = true;
  }
  return result;
}

boolean hitsCeiling() {
  boolean result = false;
  if (ballTop() <= height-height) {
  result = true;
  }
  return result;
}

boolean hitsRightWall() {
  boolean result = false;
  if (ballRight() >= width) {
    result = true;
  }
  return result;
}

boolean hitsLeftWall() {
  boolean result = false;
  if (ballLeft() <= width-width) {
  result = true;
  }
  return result;
}

boolean abovePaddle() {
  boolean result = false;
  if ((ballBottom() >= paddleY) && (ballTop() <= paddleY + paddleHeight)){
  result = true;
  }
  return result;
}

boolean paddleSurface() {
  boolean result = false;
   if ((ballX >= paddleX) && (ballX <= paddleX + paddleWidth)) {
   result = true;
   }
   return result;
}

boolean hitsBricks(){
  boolean result = false;
  for (int i = 0; i < bricks.size(); i++) {
    Brick thisBrick = bricks.get(i);
    float brickBottom = thisBrick.getY() + thisBrick.getBrickHeight();
    float brickLeft = thisBrick.getX();
    float brickRight = thisBrick.getX() + thisBrick.getBrickWidth();

    if (ballTop() <= brickBottom) {
      if (ballLeft() >= brickLeft && ballRight() <= brickRight) {
        result = true;
        bricks.remove(i);
      }
    }
  }
  return result;
}

void xVelocityChange() {
  xVelocity = xVelocity * -1;
}

void yVelocityChange() {
  yVelocity = yVelocity * -1;
}

float ballTop() {
  return ballY - radius;
}

float ballRight() {
  return ballX + radius;
}

float ballBottom() {
  return ballY + radius;
}

float ballLeft() {
  return ballX - radius;
}

class Brick {
  float x, y;
  float brickWidth, brickHeight;

  float getY() {
    return this.y;
  }

  float getX() {
    return this.x;
  }

  float getBrickHeight() {
    return this.brickHeight;
  }

  float getBrickWidth() {
    return this.brickWidth;
  }

  void setLocation(float x, float y, float brickWidth, float brickHeight){
    this.x = x;
    this.y = y;
    this.brickHeight = brickHeight;
    this.brickWidth = brickWidth;
  }

  void brickDisplay() {
    fill(255, 0, 255);
    rect(this.x, this.y, this.brickWidth, this.brickHeight);
  }
}

void mouseClicked() {
  gameInProgress = true;
  score = 0;
};
