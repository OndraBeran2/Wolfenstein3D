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

        //first intersections
        if (angle < 90){
            double x = (
                    //get next tile
                    ((int)player.getxCoor() / map.getTILE_SIZE()) + 1)
                    //get coordinate from tile
                    * map.getTILE_SIZE();
            double y = player.getyCoor();
        }
    }
}
