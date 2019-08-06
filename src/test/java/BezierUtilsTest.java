import com.google.common.collect.Lists;
import com.home.math.bezierCurve.BezierPoint;
import com.home.math.bezierCurve.BezierUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BezierUtilsTest {

    private static final double DURATION = 1;
    private static double WHOLE = 10;
    private static final double QUOTER = WHOLE / 4;

    @Test
    public void testBezierPointsCreation() {

        List<BezierPoint> startPoints = this.getstartPoints();
        Double duration = DURATION;
        List<List<BezierPoint>> points = BezierUtils.getPoints(startPoints, duration / 2, duration);

        List<BezierPoint> bezierPoints1 = points.get(1);
        List<BezierPoint> bezierPoints2 = points.get(2);
        List<BezierPoint> bezierPoints3 = points.get(3);

        List<Pair<Double, Double>> expectedPointsValues1 = Lists.newArrayList(
                Pair.of(0d, 2 * QUOTER),
                Pair.of(2 * QUOTER, WHOLE),
                Pair.of(WHOLE, WHOLE / 2)
        );

        List<Pair<Double, Double>> expectedPointsValues2 = Lists.newArrayList(
                Pair.of(QUOTER, 3 * QUOTER),
                Pair.of(3 * QUOTER, 3 * QUOTER)
        );

        List<Pair<Double, Double>> expectedPointsValues3 = Lists.newArrayList(
                Pair.of(2 * QUOTER, 3 * QUOTER)
        );

        this.assertValues(expectedPointsValues1, bezierPoints1);
        this.assertValues(expectedPointsValues2, bezierPoints2);
        this.assertValues(expectedPointsValues3, bezierPoints3);
    }

    private List<BezierPoint> getstartPoints() {

        List<BezierPoint> startPoints = new ArrayList<>();

        BezierPoint bp1 = new BezierPoint(0, 0);
        BezierPoint bp2 = new BezierPoint(0d, WHOLE);
        BezierPoint bp3 = new BezierPoint(WHOLE, WHOLE);
        BezierPoint bp4 = new BezierPoint(WHOLE, 0d);

        startPoints.add(bp1);
        startPoints.add(bp2);
        startPoints.add(bp3);
        startPoints.add(bp4);

        return startPoints;
    }

    private void assertValues(List<Pair<Double, Double>> expectedPointsValues,
                              List<BezierPoint> bezierPoints1)
    {
        assert expectedPointsValues.size() == bezierPoints1.size();

        int size = expectedPointsValues.size();
        for (int i = 0; i < size; i++) {
            Double x = expectedPointsValues.get(i).getLeft();
            Double y = expectedPointsValues.get(i).getRight();

            assert Objects.equals(bezierPoints1.get(i).x, x);
            assert Objects.equals(bezierPoints1.get(i).y, y);
        }
    }


}
