/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r23;

import battleship.interfaces.BattleshipsPlayer;
import r23.AI.R23AI;
import tournament.player.PlayerFactory;

/**
 *
 * @author MartinLodahl
 */
public class R23 implements PlayerFactory<BattleshipsPlayer>
{

    @Override
    public BattleshipsPlayer getNewInstance() {
        return new R23AI();
    }

    @Override
    public String getID() {
        return "R23";
    }

    @Override
    public String getName() {
        return "Killer Koalas";
    }

    @Override
    public String[] getAuthors() {
        String[] authors = {"Xu", "Martin"};
        return authors;
    }

   
    
}
