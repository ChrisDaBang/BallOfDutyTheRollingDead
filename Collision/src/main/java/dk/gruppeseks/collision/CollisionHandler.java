/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.collision;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.entityelements.Body;
import dk.gruppeseks.bodtrd.common.data.entityelements.Body.Geometry;
import static dk.gruppeseks.bodtrd.common.data.entityelements.Body.Geometry.CIRCLE;
import static dk.gruppeseks.bodtrd.common.data.entityelements.Body.Geometry.RECTANGLE;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
import dk.gruppeseks.bodtrd.common.data.util.Vector2;

/**
 *
 * @author Chris & Morten
 */
public class CollisionHandler
{

    /**
     * Check if 2 game objects are colliding.
     *
     * @param e1
     * game object 1.
     * @param e2
     * game object 2.
     * @return Returns true if the objects are colliding.
     */
    public static boolean isColliding(Position colPos, Body colBody, Entity e2)
    {
        Geometry e1Type = colBody.getType();

        Geometry e2Type = e2.get(Body.class).getType();

        boolean retval = false;
        if (e1Type == Geometry.CIRCLE && e2Type == Geometry.CIRCLE)
        {
            retval = collisionCircleCircle(colPos, colBody, e2);
        }
        else if (e1Type == Body.Geometry.CIRCLE && e2Type == Body.Geometry.RECTANGLE)
        {
            retval = collisionCircleRectangle(colPos, colBody, e2.get(Position.class), e2.get(Body.class));
        }
        else if (e1Type == Body.Geometry.RECTANGLE && e2Type == Body.Geometry.CIRCLE)
        {
            retval = collisionCircleRectangle(e2.get(Position.class), e2.get(Body.class), colPos, colBody);
        }
        else if (e1Type == Body.Geometry.RECTANGLE && e2Type == Body.Geometry.RECTANGLE)
        {
            retval = collisionRectangleRectangle(colPos, colBody, e2);
        }
        return retval;
    }

    /**
     * Checks if 2 circles are colliding.
     *
     * @param o1
     * Circle 1
     * @param o2
     * Circle 2
     * @return Returns true if the 2 circles are colliding.
     */
    private static boolean collisionCircleCircle(Position colPos, Body colBody, Entity e2)
    {
        double c1x = colPos.getX();
        double c2x = e2.get(Position.class).getX();
        double c1y = colPos.getY();
        double c2y = e2.get(Position.class).getY();

        double dx = c1x - c2x;
        double dy = c1y - c2y;
        double c1r = colBody.getHeight() / 2;
        double c2r = e2.get(Body.class).getHeight() / 2;

        return Math.sqrt((dx * dx) + (dy * dy)) <= (c1r + c2r);
    }

    /**
     * Checks if a circle and rectangle is colliding.
     *
     * @param circle
     * The circle
     * @param rect
     * The rectangle.
     * @return Returns true if the rectangle and the circle is colliding.
     */
    public static boolean collisionCircleRectangle(Position circPos, Body circBody, Position rectPos, Body rectBody)
    {
        Position rectCenter = getCenter(rectPos, rectBody);
        Position circleCenter = getCenter(circPos, circBody);

        double circleDistanceX = Math.abs(rectCenter.getX() - circleCenter.getX());
        double circleDistanceY = Math.abs(rectCenter.getY() - circleCenter.getY());

        if (circleDistanceY >= (rectBody.getHeight() / 2 + circBody.getHeight() / 2))
        {
            return false;
        }
        if (circleDistanceX >= (rectBody.getWidth() / 2 + circBody.getWidth() / 2))
        {
            return false;
        }
        if (circleDistanceY < (rectBody.getHeight() / 2))
        {
            return true;
        }
        if (circleDistanceX < (rectBody.getWidth() / 2))
        {
            return true;
        }
        double cornerDistanceSq = Math.sqrt(
                Math.pow((circleDistanceX - (rectBody.getWidth() / 2)), 2) + Math.pow((circleDistanceY - (rectBody.getHeight() / 2)), 2));

        return (cornerDistanceSq < circBody.getHeight() / 2);
    }

    /**
     * Checks if 2 rectangles are colliding.
     *
     * @param rect1
     * Rectangle 1
     * @param rect2
     * Rectangle 2
     * @return Returns true if the 2 rectangles are colliding
     */
    public static boolean collisionRectangleRectangle(Position colPos, Body colBody, Entity rect2)
    {

        double r1X = colPos.getX();
        double r1Y = colPos.getY();
        double r1H = colBody.getWidth();
        double r1L = colBody.getHeight();
        double r2X = rect2.get(Position.class).getX();
        double r2Y = rect2.get(Position.class).getY();
        double r2H = rect2.get(Body.class).getWidth();
        double r2L = rect2.get(Body.class).getHeight();

        boolean xOverlap = valueInRange(r1X, r2X, r2X + r2L) || valueInRange(r2X, r1X, r1X + r1L);
        boolean yOverlap = valueInRange(r1Y, r2Y, r2Y + r2H) || valueInRange(r2Y, r1Y, r1Y + r1H);
        return xOverlap && yOverlap;
    }

    /**
     * Checks if a value is within a range.
     *
     * @param value
     * The value to be checked if its within a range.
     * @param start
     * The start of the range.
     * @param end
     * The end of the range.
     * @return Returns true if the value is bigger than or equal to the start value and smaller than or equal to the end value.
     */
    private static boolean valueInRange(double value, double start, double end)
    {
        // FIXME if more methods like these pop up around the application it should be put in a new Math class.
        return (value >= start) && (value <= end);
    }

    private static Position getCenter(Position pos, Body body)
    {
        return new Position((pos.getX() + (body.getWidth() / 2)), pos.getY() + body.getHeight());
    }

    /**
     * Provides a proper response where all kinetic energy is lost.
     *
     * @param collided
     * The object that will be acting to the collision.
     * @param other
     * The object the acting object collided with.
     * @return Returns the new suggested position of the collided object.
     */
    public static Position collisionResponse(Position colPos, Body colBody, Entity other)
    {
        Geometry collidedType = colBody.getType();
        Geometry otherType = other.get(Body.class).getType();

        if (collidedType == CIRCLE && otherType == CIRCLE)
        {
            System.out.println("Circle-Circle collision");
            return collisionResponseCircleCircle(colPos, colBody, other);
        }
        else if (collidedType == CIRCLE && otherType == RECTANGLE)
        {
            System.out.println("Circle-Rectangle collision");
            return collisionResponseCircleRectangle(colPos, colBody, other.get(Position.class), other.get(Body.class));
        }
        else if (collidedType == RECTANGLE && otherType == CIRCLE)
        {
            System.out.println("Rectangle-Circle collision");
            return collisionResponseRectangleCircle(other.get(Position.class), other.get(Body.class), colPos, colBody);
        }
        else if (collidedType == RECTANGLE && otherType == RECTANGLE)
        {
            System.out.println("Rectangle-Rectangle collision");
            collisionResponseRectangleRectangle(colPos, colBody, other);
        }
        return null;
    }

    /**
     * Provides a proper response where all kinetic energy is lost for rectangle rectangle collision.
     *
     * @param collided
     * The object that will be acting to the collision.
     * @param other
     * The object the acting object collided with.
     * @return Returns the new suggested position of the collided object.
     */
    private static Position collisionResponseRectangleRectangle(Position colPos, Body colBody, Entity other)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Provides a proper response where all kinetic energy is lost for rectangle circle collision, where the rectangle is the object acting
     * on collision.
     *
     * @param collided
     * The object that will be acting to the collision.
     * @param other
     * The object the acting object collided with.
     * @return Returns the new suggested position of the collided object.
     */
    private static Position collisionResponseRectangleCircle(Position rectPos, Body rectBody, Position circPos, Body circBody)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Provides a proper response where all kinetic energy is lost for circle circle collision.
     *
     * @param collided
     * The object that will be acting to the collision.
     * @param other
     * The object the acting object collided with.
     * @return Returns the new suggested position of the collided object.
     */
    public static Position collisionResponseCircleCircle(Position colPos, Body colBody, Entity other)
    {
        double collidedx = colPos.getX();
        double otherx = other.get(Position.class).getX();
        double collidedy = colPos.getY();
        double othery = other.get(Position.class).getY();
        // http://ericleong.me/research/circle-circle/ Need this link for bullet bounce or similar.
        Vector2 distanceBetweenObjects = new Vector2(collidedx - otherx, collidedy - othery);
        distanceBetweenObjects.setMagnitude(colBody.getHeight() / 2 + other.get(Body.class).getHeight() / 2);
        return new Position(otherx + distanceBetweenObjects.getX(), othery + distanceBetweenObjects.getY());
    }

    /**
     * Provides a proper response where all kinetic energy is lost for rectangle circle collision, where the circle is the object acting on
     * collision.
     *
     * @param collided
     * The object that will be acting to the collision.
     * @param other
     * The object the acting object collided with.
     * @return Returns the new suggested position of the collided object.
     */
    public static Position collisionResponseCircleRectangle(Position circPos, Body circBody, Position rectPos, Body rectBody) // HASHTAG FUCKING CHECKED
    {
        double collidedx = circPos.getX();
        double collidedy = circPos.getY();
        double otherx = rectPos.getX();
        double othery = rectPos.getY();
        double collidedHeight = circBody.getHeight();
        double collidedWidth = circBody.getWidth();
        double otherHeight = rectBody.getHeight();
        double otherWidth = rectBody.getWidth();
        double collidedCenterX = getCenter(circPos, circBody).getX();
        double collidedCenterY = getCenter(circPos, circBody).getY();
        double otherCenterX = getCenter(rectPos, rectBody).getX();
        double otherCenterY = getCenter(rectPos, rectBody).getY();

        // If ball is to the right of other and if its center is within the
        if (collidedCenterX > otherx && collidedCenterX < otherx + otherWidth)
        {
            if (collidedCenterY < otherCenterY)
            {
                return new Position(collidedx, othery - collidedHeight);
            }
            else
            {
                return new Position(collidedx, othery + otherHeight);
            }

        }
        else if (collidedCenterY > othery && collidedCenterY < othery + otherHeight)
        {
            if (collidedCenterX < otherCenterX)
            {
                return new Position(otherx - collidedWidth, collidedy);
            }
            else
            {
                return new Position(otherx + otherWidth, collidedy);
            }
        }

        double cornerX = otherx;
        double cornerY = othery;
        if (collidedCenterX > otherx)
        {
            cornerX += otherWidth;
            if (collidedCenterY > othery)
            {
                cornerY += otherHeight;
            }

        }
        else if (collidedCenterX < otherx)
        {
            if (collidedCenterY > othery)
            {
                cornerY += otherHeight;
            }
        }
        // http://math.stackexchange.com/questions/356792/how-to-find-nearest-point-on-line-of-rectangle-from-anywhere
        Vector2 distanceBetweenObjects = new Vector2(collidedCenterX - cornerX, collidedCenterY - cornerY);
        distanceBetweenObjects.setMagnitude(rectBody.getHeight() / 2);
        return new Position(cornerX + distanceBetweenObjects.getX() - rectBody.getWidth() / 2,
                cornerY + distanceBetweenObjects.getY() - rectBody.getHeight() / 2);
    }

}
