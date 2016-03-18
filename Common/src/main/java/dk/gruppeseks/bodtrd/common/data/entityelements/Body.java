/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.bodtrd.common.data.entityelements;

/**
 *
 * @author Thanusaan
 */
public class Body
{
    private int _height;
    private int _width;
    private Geometry _type;

    public enum Geometry
    {
        CIRCLE, RECTANGLE;
        private static final Geometry[] types = Geometry.values();

        public static Geometry getType(int index)
        {
            return types[index];
        }
    }

    public Body(int height, int width)
    {
        _height = height;
        _width = width;
    }

    public Body(int height, int width, Geometry type)
    {
        _height = height;
        _width = width;
        _type = type;
    }

    public int getHeight()
    {
        return _height;
    }

    public void setHeight(int height)
    {
        this._height = height;
    }

    public int getWidth()
    {
        return _width;
    }

    public void setWidth(int width)
    {
        this._width = width;
    }

    public Geometry getType()
    {
        return _type;
    }

    public void setType(Geometry _type)
    {
        this._type = _type;
    }

}
