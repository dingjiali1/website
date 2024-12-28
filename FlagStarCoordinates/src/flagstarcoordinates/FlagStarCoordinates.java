public class Main { 
    public static void main(String[] args) { 
        // 获取大星坐标 
        int[][] largeStarCoordinates = FlagStarCoordinates.starCordinate(true, 0);
        printCoordinates(largeStarCoordinates); 

        // 获取第一颗小星坐标 
        int[][] smallStar1Coordinates = FlagStarCoordinates.starCordinate(false, 1);
        printCoordinates(smallStar1Coordinates); 

        // 依次可以获取其他小星坐标进行测试 
    } 

    private static void printCoordinates(int[][] coordinates) { 
        for (int i = 0; i < coordinates.length; i++) { 
            System.out.println("顶点 " + i + " 坐标: (" + coordinates[i][0] + ", " + coordinates[i][1] + ")");
        } 
    } 
} 