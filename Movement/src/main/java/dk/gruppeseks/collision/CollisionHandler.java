///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package dk.gruppeseks.collision;
//
//import dk.gruppeseks.bodtrd.common.data.Entity;
//import dk.gruppeseks.bodtrd.common.data.entityelements.Body;
//import dk.gruppeseks.bodtrd.common.data.entityelements.Body.Geometry;
//import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
//
///**
// *
// * @author Chris
// */
//public class CollisionHandler
//{
//
//    /**
//     * Check if 2 game objects are colliding.
//     *
//     * @param e1
//     * game object 1.
//     * @param e2
//     * game object 2.
//     * @return Returns true if the objects are colliding.
//     */
//    public static boolean isColliding(Entity e1, Entity e2)
//    {
//        Position e1Pos = e1.get(Position.class);
//        Geometry e1Type = e1.get(Body.class).getType();
//
//        Position e2Pos = e2.get(Position.class);
//        Geometry e2Type = e2.get(Body.class).getType();
//
//        boolean retval = false;
//        if (e1Type == Geometry.CIRCLE && e2Type == Geometry.CIRCLE)
//        {
//            retval = collisionCircleCircle(e1, e2);
//        }
//        else if (e1Type == Body.Geometry.CIRCLE && e2Type == Body.Geometry.RECTANGLE)
//        {
//            retval = collisionCircleRectangle(e1, e2);
//        }
//        else if (e1Type == Body.Geometry.RECTANGLE && e2Type == Body.Geometry.CIRCLE)
//        {
//            retval = collisionCircleRectangle(e2, e1);
//        }
//        else if (e1Type == Body.Geometry.RECTANGLE && e2Type == Body.Geometry.RECTANGLE)
//        {
//            retval = collisionRectangleRectangle(e1, e2);
//        }
//        return retval;
//    }
//
//    /**
//     * Checks if 2 circles are colliding.
//     *
//     * @param o1
//     *            Circle 1
//     * @param o2
//     *            Circle 2
//     * @return Returns true if the 2 circles are colliding.
//     */
//    private static boolean collisionCircleCircle(Entity e1, Entity e2)
//    {
//        double c1x = e1.get(Position.class).getX();
//        double c2x = e2.get(Position.class).getX();
//        double c1y = e1.get(Position.class).getY();
//        double c2y = e2.get(Position.class).getY();
//
//        double dx = c1x - c2x;
//        double dy = c1y - c2y;
//        double c1r = e1.get(Body.class).getHeight()/ 2;
//        double c2r = e2.get(Body.class).getHeight() / 2;
//
//        return Math.sqrt((dx * dx) + (dy * dy)) <= (c1r + c2r);
//    }
//
//    /**
//     * Checks if a circle and rectangle is colliding.
//     *
//     * @param circle
//     *            The circle
//     * @param rect
//     *            The rectangle.
//     * @return Returns true if the rectangle and the circle is colliding.
//     */
//    public static boolean collisionCircleRectangle(Entity circle, Entity rect)
//    {
//        double circleDistanceX = Math.abs(rect.getCenter().getX() - circle.getCenter().getX());
//        double circleDistanceY = Math.abs(rect.getCenter().getY() - circle.getCenter().getY());
//
//        if (circleDistanceY >= (rect.get(Body.class).getHeight()/ 2 + circle.get(Body.class).getHeight()/ 2))
//        {
//            return false;
//        }
//        if (circleDistanceX >= (rect.get(Body.class).getWidth() / 2 + circle.get(Body.class).getWidth() / 2))
//        {
//            return false;
//        }
//        if (circleDistanceY < (rect.get(Body.class).getHeight() / 2))
//        {
//            return true;
//        }
//        if (circleDistanceX < (rect.get(Body.class).getWidth() / 2))
//        {
//            return true;
//        }
//        double cornerDistanceSq = Math.sqrt(
//                Math.pow((circleDistanceX - (rect.get(Body.class).getWidth() / 2)), 2) + Math.pow((circleDistanceY - (rect.get(Body.class).getHeight() / 2)), 2));
//
//        return (cornerDistanceSq < circle.get(Body.class).getHeight() / 2);
//    }
//
//      /**
//     * Checks if 2 rectangles are colliding.
//     *
//     * @param rect1
//     *            Rectangle 1
//     * @param rect2
//     *            Rectangle 2
//     * @return Returns true if the 2 rectangles are colliding
//     */
//    public static boolean collisionRectangleRectangle(Entity rect1, Entity rect2)
//    {
//
//        double r1X = rect1.get(Position.class).getX();
//        double r1Y = rect1.get(Position.class).getY();
//        double r1H = rect1.get(Body.class).getWidth();
//        double r1L = rect1.get(Body.class).getHeight();
//        double r2X = rect2.get(Position.class).getX();
//        double r2Y = rect2.get(Position.class).getY();
//        double r2H = rect2.get(Body.class).getWidth();
//        double r2L = rect2.get(Body.class).getHeight();
//
//        boolean xOverlap = valueInRange(r1X, r2X, r2X + r2L) || valueInRange(r2X, r1X, r1X + r1L);
//        boolean yOverlap = valueInRange(r1Y, r2Y, r2Y + r2H) || valueInRange(r2Y, r1Y, r1Y + r1H);
//        return xOverlap && yOverlap;
//    }
//
//    /**
//     * Checks if a value is within a range.
//     *
//     * @param value
//     *            The value to be checked if its within a range.
//     * @param start
//     *            The start of the range.
//     * @param end
//     *            The end of the range.
//     * @return Returns true if the value is bigger than or equal to the start value and smaller than or equal to the end value.
//     */
//    private static boolean valueInRange(double value, double start, double end)
//    {
//        // FIXME if more methods like these pop up around the application it should be put in a new Math class.
//        return (value >= start) && (value <= end);
//    }
//}
