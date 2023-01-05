package model;

public class MainModel {
    private final Map map;
    private final Player player;

    private int windowWidth;

    public MainModel() {
        map = new Map();
        player = new Player(100, 100, 45);
    }

    private double castRay(double angle){
        Ray ray = new Ray(player.getxCoor(), player.getyCoor(), angle);

        double x = xLineIntersectDist(ray);
        double y = yLineIntersectDist(ray);

        return Double.min(x, y);
    }

    public static void main(String[] args) {
//        MainModel m = new MainModel(new Map(), new Player(150, 150, 315));
//        Point p = m.firsIntersectY(new Ray(m.player.getxCoor(), m.player.getyCoor(), m.player.getAngle()));
//        System.out.println(p.getX() + " " + (int)p.getY());
    }
    /** @noinspection IntegerDivisionInFloatingPointContext*/
    private Point firsIntersectY(Ray ray){
        double x, y;
        if (ray.getAngle() < 90 || ray.getAngle() >= 270){
            x = (
                    //get next tile
                    (((int)ray.getxCoor() / map.getTILE_SIZE()) + 1)
                    //get coordinate from tile
                    * map.getTILE_SIZE()
                );
            //distance to next y line of grid
            double distFromY = map.getTILE_SIZE() - (ray.getyCoor() % map.getTILE_SIZE());
            //calculates the distance to next x line, positive for rays going up
            double distFromX = Math.tan(Math.toRadians(ray.getAngle())) * distFromY;

            y = ray.getyCoor() - distFromX;
        }
        else {  //beginning of tile
            x = ((int)ray.getxCoor() / map.getTILE_SIZE()) * map.getTILE_SIZE();

            double distFromY = ray.getxCoor() - x;
            double distFromX = Math.tan(Math.toRadians(ray.getAngle())) * distFromY;

            y = ray.getyCoor() + distFromX;
        }

        return new Point(x, y);
    }

    /** @noinspection IntegerDivisionInFloatingPointContext*/
    private Point firstIntersectX(Ray ray){
        double x, y;
        if (ray.getAngle() < 180){
            y = (
                    ((int)ray.getyCoor() / map.getTILE_SIZE())
                    * map.getTILE_SIZE()
                    );

            double distFromX = ray.getyCoor() - y;
            double distFromY = distFromX / Math.tan(Math.toRadians(ray.getAngle()));

            x = ray.getxCoor() + distFromY;
        }
        else {
            y = (
                    (((int)ray.getyCoor() / map.getTILE_SIZE()) + 1)
                    * map.getTILE_SIZE()
                    );

            double distFromX = y - ray.getyCoor();
            double distFromY = distFromX / Math.tan(Math.toRadians(ray.getAngle()));

            x = ray.getxCoor() - distFromY;
        }
        return new Point(x, y);
    }

    private double xLineIntersectDist(Ray ray){
        Point firstIntersectX = firstIntersectX(ray);

        double deltaX = xIntersectDeltaX(ray.getAngle());
        double deltaY = xIntersectDeltaY(ray.getAngle());

        Point nextIntersectX = new Point(firstIntersectX.getX(), firstIntersectX.getY());

        while (map.inBounds(nextIntersectX)){
            //step into cell
            Point temp;
            double tempY;

            if (ray.getAngle() < 180){
                tempY = nextIntersectX.getY() - 1;
            } else {
                tempY = nextIntersectX.getY() + 1;
            }

            temp = new Point(nextIntersectX.getX(), tempY);

            //check for walls
            if (map.isWall(temp)){
                return Point.distance(nextIntersectX, new Point(ray.getxCoor(), ray.getyCoor()));
            } else {
                double newX = nextIntersectX.getX() + deltaX;
                double newY = nextIntersectX.getY() + deltaY;
                nextIntersectX = new Point(newX, newY);
            }
        }

        return Double.MAX_VALUE;
    }

    private double yLineIntersectDist(Ray ray){
        Point firstIntersect = firsIntersectY(ray);

        double deltaX = yIntersectDeltaX(ray.getAngle());
        double deltaY = yIntersectDeltaY(ray.getAngle());

        Point nextIntersect = new Point(firstIntersect.getX(), firstIntersect.getY());

        while (map.inBounds(nextIntersect)){
            Point temp;
            double tempX;
            if (ray.getAngle() > 90 && ray.getAngle() <= 270){
                tempX = nextIntersect.getX() - 1;
            } else {
                tempX = nextIntersect.getX() + 1;
            }

            temp = new Point(tempX, nextIntersect.getY());

            if (map.isWall(temp)){
                return Point.distance(nextIntersect, new Point(ray.getxCoor(), ray.getyCoor()));
            } else {
                double newX = nextIntersect.getX() + deltaX;
                double newY = nextIntersect.getY() + deltaY;
                nextIntersect = new Point(newX, newY);
            }

        }

        return Double.MAX_VALUE;
    }

    private double xIntersectDeltaX(double angle){
        if(angle <= 90) {
            //deltaX must be positive
            return Math.pow(Math.tan(Math.toRadians(angle)), -1) * map.getTILE_SIZE();
        } else if (angle > 90 && angle <= 180){
            return Math.pow(Math.tan(Math.toRadians(180 - angle)), 1) * map.getTILE_SIZE() * -1;
        } else if (angle > 180 && angle <= 270){
            return Math.pow(Math.tan(Math.toRadians(angle - 180)), -1) * map.getTILE_SIZE() * -1;
        } else {
            return Math.pow(Math.tan(Math.toRadians(360 - angle)), -1) * map.getTILE_SIZE();
        }
    }

    private double xIntersectDeltaY(double angle){
        if(angle <= 90) {
            //deltaX must be positive
            return -1 * map.getTILE_SIZE();
        } else if (angle > 90 && angle <= 180){
            return -1 * map.getTILE_SIZE();
        } else if (angle > 180 && angle <= 270){
            return map.getTILE_SIZE();
        } else {
            return map.getTILE_SIZE();
        }
    }

   private double yIntersectDeltaX(double angle){
       if(angle <= 90) {
           return map.getTILE_SIZE();
       } else if (angle > 90 && angle <= 180){
           return -1 * map.getTILE_SIZE();
       } else if (angle > 180 && angle <= 270){
           return -1 * map.getTILE_SIZE();
       } else {
           return map.getTILE_SIZE();
       }
   }

   private double yIntersectDeltaY(double angle){
        if (angle <= 90){
            return Math.tan(Math.toRadians(angle)) * map.getTILE_SIZE() * -1;
        } else if (angle > 90 && angle <= 180){
            return Math.tan(Math.toRadians(180 - angle)) * map.getTILE_SIZE() * -1;
        } else if (angle > 180 && angle <= 270){
            return Math.tan(Math.toRadians(angle - 180)) * map.getTILE_SIZE();
        } else {
            return Math.tan(Math.toRadians(360 - angle)) * map.getTILE_SIZE();
        }
   }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }
}
