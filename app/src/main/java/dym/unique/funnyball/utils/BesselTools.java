package dym.unique.funnyball.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daiyiming on 2016/4/27.
 */
public class BesselTools {

    public static class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

    }

    private BesselTools() {}

    /**
     * 计算点
     *
     * @param points 路径上的点集
     * @param rate   比率
     */
    public static Point calculatePoint(List<Point> points, float rate) {
        if (points == null || points.size() == 0) {
            return null;
        }
        // 一纬贝塞尔曲线
        if (points.size() == 1) {
            return points.get(0);
        }
        // 二维贝塞尔曲线
        if (points.size() == 2) {
            Point first = points.get(0), second = points.get(1);
            return new Point(first.x + (second.x - first.x) * rate, first.y + (second.y - first.y) * rate);
        }
        // 多维贝塞尔曲线
        List<Point> res = new ArrayList<>();
        for (int i = 1; i < points.size(); i++) {
            List<Point> params = new ArrayList<>();
            params.add(points.get(i - 1));
            params.add(points.get(i));
            res.add(calculatePoint(params, rate));
        }
        return calculatePoint(res, rate);
    }

}
