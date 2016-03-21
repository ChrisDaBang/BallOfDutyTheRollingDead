/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.collision;

import dk.gruppeseks.bodtrd.common.data.Entity;
import static dk.gruppeseks.bodtrd.common.data.EntityType.PLAYER;
import static dk.gruppeseks.bodtrd.common.data.EntityType.PROJECTILE;
import dk.gruppeseks.bodtrd.common.data.GameData;
import dk.gruppeseks.bodtrd.common.data.entityelements.Body;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
import dk.gruppeseks.bodtrd.common.data.entityelements.Velocity;
import dk.gruppeseks.bodtrd.common.interfaces.IEntityProcessor;
import java.util.Map;

/**
 *
 * @author Chris
 */
public class CollisionProcessor implements IEntityProcessor
{
    @Override
    public void process(GameData gameData, Map<Integer, Entity> world)
    {

        double dt = gameData.getDeltaTime();

        for (Entity e : world.values())
        {
            Position p = e.get(Position.class);
            Velocity velocity = e.get(Velocity.class);

            if (p == null || velocity == null)
            {
                continue;
            }

//            // Temp test for collision
//            if (e.getType() == PLAYER)
//            {
//                for (Entity ent : world.values())
//                {
//                    if (ent.getType() == PLAYER)
//                    {
//                    }  //Don't collide with itself
//                    else if (CollisionHandler.isColliding(e, ent))
//                    {
//                        System.out.println("Collision true");
//                        p.setPosition(CollisionHandler.collisionResponse(e, ent));
//                        p.setX(CollisionHandler.collisionResponse(e, ent).getX());
//                        p.setY(CollisionHandler.collisionResponse(e, ent).getY());
//                    }
//                }
//            }
//            // Temp IMPROVED test for collision, ported over from the old system. ISN'T WORKING
            if (e.getType() == PLAYER)
            {

                Position tempPos = new Position(p.getX(), p.getY());
                Body tempBody = new Body(e.get(Body.class).getHeight(), e.get(Body.class).getWidth(), e.get(Body.class).getType());

//                temp.getBody().increasePosition(velocity.getX() * secondsSinceLastUpdate,
//                        velocity.getY() * secondsSinceLastUpdate);
                boolean collision = false;

                for (Entity ent : world.values())
                {

                    if (ent.getID() == e.getID() || ent.getType() == PROJECTILE)
                    {

                    }
                    else if (CollisionHandler.isColliding(tempPos, tempBody, ent))
                    {

                        System.out.println("Collision true"); // old stuff, delete if working.
//                        p.setX(CollisionHandler.collisionResponse(temp, ent).getX());
//                        p.setY(CollisionHandler.collisionResponse(temp, ent).getY());

                        tempPos.setPosition(CollisionHandler.collisionResponse(tempPos, tempBody, ent)); // Gives new position so it doesnt collide
                        p.setPosition(tempPos);

                        if (true)
                        {
                            break;
                        }
                        for (Entity ent2 : world.values()) // Checks if it collides with anything.
                        {
                            if (ent2.getID() == e.getID() || ent2.getID() == ent.getID() || ent2.getType() == PROJECTILE)
                            {
                                continue;
                            }
                            if (CollisionHandler.isColliding(tempPos, tempBody, ent2))
                            {
                                for (Entity ent3 : world.values())
                                {
                                    if (ent3.getID() == e.getID() || ent3.getID() == ent.getID()
                                            || ent3.getID() == ent2.getID() || ent3.getType() == PROJECTILE)
                                    {
                                        continue;
                                    }
                                    if (CollisionHandler.isColliding(tempPos, tempBody, ent3))
                                    {
                                        collision = true;
                                        break;
                                    }

                                }
                                if (!collision)
                                {
                                    // if it doesnt collide with a third object, put it to the secondcalculated position.
                                    p.setPosition(CollisionHandler.collisionResponse(tempPos, tempBody, ent2));
                                }
                                collision = true;
                                break;
                            }

                        }
                        if (!collision) // if it doesnt collide with a second object, put it to the first calculated position.
                        {
                            p.setPosition(tempPos);
                        }
                        collision = true; // with the collided object anymore.
                        break;
                    }
                }
            }
        }
    }

}
