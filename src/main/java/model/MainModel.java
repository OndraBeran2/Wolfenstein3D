package model;

public class MainModel {
    private Map map;
    private Player player;

    public MainModel(Map map, Player player) {
        this.map = map;
        this.player = player;
    }

    private double castRay(double angle){
        Ray ray = new Ray(player.getxCoor(), player.getyCoor(), angle);
        Point firstIntersectX = firstIntersectX(ray);
        Point firstIntersectY = firsIntersectY(ray);
        
        return 0;
    }

    public static void main(String[] args) {
        MainModel m = new MainModel(new Map(), new Player(150, 150, 315));
        Point p = m.firsIntersectY(new Ray(m.player.getxCoor(), m.player.getyCoor(), m.player.getAngle()));
        System.out.println(p.getX() + " " + (int)p.getY());
    }
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
}
