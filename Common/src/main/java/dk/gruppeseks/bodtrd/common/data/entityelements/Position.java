/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data.entityelements;

import java.io.Serializable;

/**
 *
 * @author Morten
 */
public class Position implements Serializable
{
    private double _x;
    private double _y;

    public Position(double x, double y)
    {
        this._x = x;
        this._y = y;
    }

    public double getX()
    {
        return _x;
    }

    public void setX(double x)
    {
        this._x = x;
    }

    public double getY()
    {
        return _y;
    }

    public void setY(double y)
    {
        this._y = y;
    }

    /**
     * Sets this positions x and y equal to the other positions x and y.
     *
     * @param pos
     */
    public void setPosition(Position pos)
    {
        this._x = pos.getX();
        this._y = pos.getY();
    }
}
