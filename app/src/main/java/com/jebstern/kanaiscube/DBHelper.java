package com.jebstern.kanaiscube;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kanaiscube.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "armors";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AFFIX = "affix";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_MODE_SEASON = "season";
    public static final String COLUMN_MODE_NORMAL = "normal";
    public static final String COLUMN_MODE_STASH = "stash";

    private static final String CREATE_TABLE_ARMORS = "create table " + TABLE_NAME
            + " ("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text DEFAULT NULL, "
            + COLUMN_AFFIX + " text DEFAULT NULL, "
            + COLUMN_TYPE + " text DEFAULT NULL, "
            + COLUMN_MODE_SEASON + " integer DEFAULT 0, "
            + COLUMN_MODE_NORMAL + " integer DEFAULT 0, "
            + COLUMN_MODE_STASH + " integer DEFAULT 0 )";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ARMORS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS armors");
        onCreate(db);
    }

    public void resetCubedData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + COLUMN_MODE_SEASON + " =0, " + COLUMN_MODE_NORMAL + "=0");
        db.close();
    }


    public int getItemAmount(String type, String mode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        if (mode.equalsIgnoreCase("season")) {
            cursor = db.rawQuery("SELECT COUNT (*) FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + "=" + type + " AND " + COLUMN_MODE_SEASON + "=1", null);

        } else {
            cursor = db.rawQuery("SELECT COUNT (*) FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + "=" + type + " AND " + COLUMN_MODE_NORMAL + "=1", null);
        }

        int count = 0;

        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }

            cursor.close();
        }


        db.close();
        return count;
    }


    public boolean updateItem(Items item, String column) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (column.equalsIgnoreCase("season")) {
            contentValues.put(COLUMN_MODE_SEASON, item.getSeasonMode());
        } else if (column.equalsIgnoreCase("normal")) {
            contentValues.put(COLUMN_MODE_NORMAL, item.getNormalMode());
        } else {
            contentValues.put(COLUMN_MODE_STASH, 1);
        }

        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ? ", new String[]{Integer.toString(item.getId())});
        return true;
    }

    public List<Items> searchItems(String query) {
        List<Items> items = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + query + " ORDER BY " + COLUMN_NAME + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Items item = new Items();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setAffix(cursor.getString(2));
                item.setType(cursor.getString(3));
                item.setSeasonMode(cursor.getInt(4));
                item.setNormalMode(cursor.getInt(5));
                item.setStashMode(cursor.getInt(6));
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public List<Items> getAllItems(String query) {
        List<Items> items = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + query + " ORDER BY " + COLUMN_NAME + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Items item = new Items();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setAffix(cursor.getString(2));
                item.setType(cursor.getString(3));
                item.setSeasonMode(cursor.getInt(4));
                item.setNormalMode(cursor.getInt(5));
                item.setStashMode(cursor.getInt(6));
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }


    public void initializeJewelry() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, "Arcstone");
        contentValues.put(COLUMN_AFFIX, "Lightning pulses periodically between all wearers of this item, dealing 1500% weapon damage.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Avarice Band");
        contentValues.put(COLUMN_AFFIX, "Each time you pick up gold, increase your Gold and Health Pickup radius by 1 yard for 10 seconds, stacking up to 30 times");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Band of Hollow Whispers");
        contentValues.put(COLUMN_AFFIX, "This ring occasionally haunts nearby enemies.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Band of the Rue Chambers");
        contentValues.put(COLUMN_AFFIX, "Your Spirit Generators generate 50% more Spirit.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Broken Promises");
        contentValues.put(COLUMN_AFFIX, "After 5 consecutive non-critical hits, your chance to critically hit is increased to 100% for 3seconds.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Bul'Kathos's Wedding Band");
        contentValues.put(COLUMN_AFFIX, "You drain life from enemies around you.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Convention of Elements");
        contentValues.put(COLUMN_AFFIX, "Gain 200% increased damage to a single element for 4 seconds. This effect rotates through the elements available to your class in the following order: Arcane, Cold, Fire, Holy, Lightning, Physical, Poison.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Countess Julia's Cameo");
        contentValues.put(COLUMN_AFFIX, "Prevent all Arcane damage taken and heal yourself for 25% of the amount prevented.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Dovu Energy Trap");
        contentValues.put(COLUMN_AFFIX, "Increases duration of Stun effects by 25%.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Eternal Union");
        contentValues.put(COLUMN_AFFIX, "Increases the duration of Phalanx avatars by 200%.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Golden Gorget of Leoric");
        contentValues.put(COLUMN_AFFIX, "After earning a massacre bonus, 6 Skeletons are summoned to fight by your side for 10 seconds");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Halo of Arlyse");
        contentValues.put(COLUMN_AFFIX, "Your Ice Armor now reduces damage from melee attacks by 60% and automatically casts Frost Nova whenever you take 10% of your Life in damage.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Hellfire Ring");
        contentValues.put(COLUMN_AFFIX, "Chance on hit to engulf the ground in lava, dealing 200% weapon damage per second for 6 seconds.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Krede's Flame");
        contentValues.put(COLUMN_AFFIX, "Taking Fire damage restores your primary resource.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Kymbo's Gold");
        contentValues.put(COLUMN_AFFIX, "Picking up gold heals you for an amount equal to the gold that was picked up.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Mara's Kaleidoscope");
        contentValues.put(COLUMN_AFFIX, "Prevent all Poison damage taken and heal yourself for 15% of the amount prevented.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Moonlight Ward");
        contentValues.put(COLUMN_AFFIX, "Hitting an enemy within 15 yards has a chance to ward you with shards of Arcane energy that explode when enemies get close, dealing 320% weapon damage as Arcane to enemies within 15 yards.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Nagelring");
        contentValues.put(COLUMN_AFFIX, "Summons a Fallen Lunatic to your side every 10 seconds.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Obsidian Ring of the Zodiac");
        contentValues.put(COLUMN_AFFIX, "Reduce the remaining cooldown of one of your skills by 1 seconds when you hit with a resource-spending attack.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Oculus Ring");
        contentValues.put(COLUMN_AFFIX, "Chance to create an area of focused power on killing a monster. Damage is increased by 40% while standing in the area.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Overwhelming Desire");
        contentValues.put(COLUMN_AFFIX, "Chance on hit to charm the enemy. While charmed, the enemy takes 35% more damage");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Pandemonium Loop");
        contentValues.put(COLUMN_AFFIX, "Enemies slain while Feared die in a bloody explosion for 800% weapon damage and cause other nearby enemies to flee in Fear");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Puzzle Ring");
        contentValues.put(COLUMN_AFFIX, "Summon a treasure goblin who picks up normal-quality items for you. After picking up 12 items, he drops a rare item with a chance for a legendary.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Rakoff's Glass of Life");
        contentValues.put(COLUMN_AFFIX, "Enemies you kill have a 4% additional chance to drop a health globe.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Rechel's Ring of Larceny");
        contentValues.put(COLUMN_AFFIX, "Gain 60% increased movement speed for 4 seconds after Fearing an enemy.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Ring of Royal Grandeur");
        contentValues.put(COLUMN_AFFIX, "Reduces the number of items needed for set bonuses by 1 (to a minimum of 2).");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Rogar's Huge Stone");
        contentValues.put(COLUMN_AFFIX, "Increase your Life per Second by up to 100% based on your missing Life.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Skull Grasp");
        contentValues.put(COLUMN_AFFIX, "Increase the damage of Whirlwind by 400% weapon damage.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Talisman of Aranoch");
        contentValues.put(COLUMN_AFFIX, "Prevent all Cold damage taken and heal yourself for 15% of the amount prevented.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Ess of Johan");
        contentValues.put(COLUMN_AFFIX, "Chance on hit to pull in enemies toward your target and Slow them by 80%.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Short Man's Finger");
        contentValues.put(COLUMN_AFFIX, "Gargantuan instead summons three smaller gargantuans each more powerful than before.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Star of Azkaranth");
        contentValues.put(COLUMN_AFFIX, "Prevent all Fire damage taken and heal yourself for 15% of the amount prevented.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Tall Man's Finger");
        contentValues.put(COLUMN_AFFIX, "Zombie Dogs instead summons a single gargantuan dog with more damage and health than all other dogs combined.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Unity");
        contentValues.put(COLUMN_AFFIX, "All damage taken is split between wearers of this item.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Wyrdward");
        contentValues.put(COLUMN_AFFIX, "Lightning damage has a 35% chance to Stun for 1.5 seconds.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Xephirian Amulet");
        contentValues.put(COLUMN_AFFIX, "Prevent all Lightning damage taken and heal yourself for 15% of the amount prevented.");
        contentValues.put(COLUMN_TYPE, "jewelry");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        db.close();
    }


    public void initializeWeapons() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, "Aether Walker");
        contentValues.put(COLUMN_AFFIX, "Teleport no longer has a cooldown but costs 25 Arcane Power.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Ahavarion,Spear of Lycander");
        contentValues.put(COLUMN_AFFIX, "Chance on killing a demon to gain a random Shrine effect.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Akarat's Awakening");
        contentValues.put(COLUMN_AFFIX, "Every successful block has a 25% chance to reduce all cooldowns by 1 second.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Anessazi Edge");
        contentValues.put(COLUMN_AFFIX, "Zombie Dogs stuns enemies around them for 1.5 seconds when summoned.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Arreat's Law");
        contentValues.put(COLUMN_AFFIX, "Weapon Throw generates up to 15-20 additional Fury based on how far away the enemy hit is. Maximum benefit when the enemy hit is 20 or more yards away.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Arthef's Spark of Life");
        contentValues.put(COLUMN_AFFIX, "Heal for 4% of your missing Life when you kill an Undead enemy.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Azurewrath");
        contentValues.put(COLUMN_AFFIX, "Undead and Demon enemies within 25 yards take 650% weapon damage as Holy every second and are sometimes knocked into the air.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Baleful Remnant");
        contentValues.put(COLUMN_AFFIX, "Enemies killed while Akarat's Champion is active turn into Phalanx Avatars for 10 seconds.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Bastion's Revered");
        contentValues.put(COLUMN_AFFIX, "Frenzy now stacks up to 10 times.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Blade of Prophecy");
        contentValues.put(COLUMN_AFFIX, "Two Condemned enemies also trigger Condemn's explosion.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Blood Brother");
        contentValues.put(COLUMN_AFFIX, "Grants a 20% chance to block attacks. Blocked attacks inflict 30% less damage. After blocking an attack, your next attack inflicts 30% additional damage.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Bombardier's Rucksack");
        contentValues.put(COLUMN_AFFIX, "You may have 2 additional Sentries.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Bovine Bardiche");
        contentValues.put(COLUMN_AFFIX, "Chance on hit to summon a herd of murderous cows.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Buriza-Do Kyanon");
        contentValues.put(COLUMN_AFFIX, "Your projectiles pierce 2 additional times.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Burst of Wrath");
        contentValues.put(COLUMN_AFFIX, "Killing enemies and destroying objects has a chance to grant 20% of your maximum primary resource");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Butcher's Carver");
        contentValues.put(COLUMN_AFFIX, "The Butcher still inhabits his carver.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Calamity");
        contentValues.put(COLUMN_AFFIX, "Enemies you hit become Marked for Death.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Cam's Rebuttal");
        contentValues.put(COLUMN_AFFIX, "Falling Sword can be used again within 4 seconds before the cooldown is triggered.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Chanon Bolter");
        contentValues.put(COLUMN_AFFIX, "Your Spike Traps lure enemies to them. Enemies may be taunted once every 12 seconds.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Cinder Switch");
        contentValues.put(COLUMN_AFFIX, "50% chance to cast a fireball when attacking.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Cluckeye");
        contentValues.put(COLUMN_AFFIX, "50% chance to cluck when attacking.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Cosmic Strand");
        contentValues.put(COLUMN_AFFIX, "Teleport gains the effect of the Wormhole rune.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Coven's Criterion");
        contentValues.put(COLUMN_AFFIX, "You take 60% less damage from blocked attacks.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Danetta's Revenge");
        contentValues.put(COLUMN_AFFIX, "Vault gains the effect of the Rattling Roll rune.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Danetta's Spite");
        contentValues.put(COLUMN_AFFIX, "Leave a clone of yourself behind after using Vault.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Darklight");
        contentValues.put(COLUMN_AFFIX, "Fist of the Heavens has a 60% chance to also be cast at your location.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Dead Man's Legacy");
        contentValues.put(COLUMN_AFFIX, "Multishot hits enemies below 60% health twice.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Deadly Rebirth");
        contentValues.put(COLUMN_AFFIX, "Grasp of the Dead gains the effect of the Rain of Corpses rune.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Defender of Westmarch");
        contentValues.put(COLUMN_AFFIX, "Blocks have a chance of summoning a charging wolf that deals 1000% weapon damage to all enemies it passes through.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Demon Machine");
        contentValues.put(COLUMN_AFFIX, "65% chance to shoot explosive bolts when attacking.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Denial");
        contentValues.put(COLUMN_AFFIX, "Each enemy hit by your Sweep Attack increases the damage of your next Sweep Attack by 40%, stacking up to 5 times.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Dishonored Legacy");
        contentValues.put(COLUMN_AFFIX, "Cleave deals up to 400% increased damage based on percentage on missing Fury.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Eberli Charo");
        contentValues.put(COLUMN_AFFIX, "Reduces the cooldown of Heaven's Fury by 50%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Emimei's Duffel");
        contentValues.put(COLUMN_AFFIX, "Bolas now explode instantly.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Envious Blade");
        contentValues.put(COLUMN_AFFIX, "Gain 100% Critical Hit Chance against enemies at full health");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Eun-jang-do");
        contentValues.put(COLUMN_AFFIX, "Attacking enemies below 20% Life freezes them for 3 seconds.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Fate of the Fell");
        contentValues.put(COLUMN_AFFIX, "Gain two additional rays of Heaven’s Fury.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Fjord Cutter");
        contentValues.put(COLUMN_AFFIX, "You are surrounded by a Chilling Aura when attacking.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Flying Dragon");
        contentValues.put(COLUMN_AFFIX, "Chance to double your attack speed when attacking.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Freeze of Deflection");
        contentValues.put(COLUMN_AFFIX, "Blocking an attack has a chance to Freeze the attacker for 1.5 seconds.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Frydehr's Wrath");
        contentValues.put(COLUMN_AFFIX, "Condemn has no cooldown but instead costs 40 Wrath.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Fulminator");
        contentValues.put(COLUMN_AFFIX, "Lightning damage has a chance to turn enemies into lightning rods, causing them to pulse 555% weapon damage as Lightning every second to nearby enemies for 6 seconds.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Fury of the Vanished Peak");
        contentValues.put(COLUMN_AFFIX, "Reduces the Fury cost of Seismic Slam by 40–50%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Genzaniku");
        contentValues.put(COLUMN_AFFIX, "Chance to summon a ghostly Fallen Champion when attacking.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Gesture of Orpheus");
        contentValues.put(COLUMN_AFFIX, "Reduces the cooldown of Slow Time by 40%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Golden Flense");
        contentValues.put(COLUMN_AFFIX, "Sweep Attack restores 6 Wrath for each enemy hit.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Golden Scourge");
        contentValues.put(COLUMN_AFFIX, "Smite now jumps to 3 additional enemies.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Guard of Johanna");
        contentValues.put(COLUMN_AFFIX, "Blessed Hammer damage is increased by 250% for the first 3 enemies it hits.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Gyrfalcon's Foote");
        contentValues.put(COLUMN_AFFIX, "Removes the resource cost of Blessed Shield.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Hack");
        contentValues.put(COLUMN_AFFIX, "100% of your Thorns damage is applied on every attack.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Hallowed Bulwark");
        contentValues.put(COLUMN_AFFIX, "Iron Skin also increases your Block Amount by 60%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Hellrack");
        contentValues.put(COLUMN_AFFIX, "Chance to root enemies to the ground when you hit them.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Hellskull");
        contentValues.put(COLUMN_AFFIX, "Gain 10% increased damage while wielding a two-handed weapon.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Helltrapper");
        contentValues.put(COLUMN_AFFIX, "10% chance on hit to summon a Spike Trap, Caltrops or Sentry");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Henri's Perquisition");
        contentValues.put(COLUMN_AFFIX, "The first time an enemy deals damage to you, reduce that damage by 45-60% and Charm the enemy for 3 seconds.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Homunculus");
        contentValues.put(COLUMN_AFFIX, "A Zombie Dog is automatically summoned to your side every 2 seconds.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "In-geom");
        contentValues.put(COLUMN_AFFIX, "Your skill cooldowns are reduced by 10 seconds for 15 seconds after killing an elite pack.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Incense Torch of the Grand Temple");
        contentValues.put(COLUMN_AFFIX, "Reduces the Spirit cost of Wave of Light by 50%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Inviolable Faith");
        contentValues.put(COLUMN_AFFIX, "Casting Consecration also casts Consecration beneath all of your allies.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Ivory Tower");
        contentValues.put(COLUMN_AFFIX, "Blocks release forward a Fires of Heaven.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Jace's Hammer of Vigilance");
        contentValues.put(COLUMN_AFFIX, "Increase the size of your Blessed Hammers.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Jawbreaker");
        contentValues.put(COLUMN_AFFIX, "When Dashing Strike hits an enemy more than 30 yards away, its Charge cost is refunded.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Jekangbord");
        contentValues.put(COLUMN_AFFIX, "Blessed Shield ricochets to 6 additional enemies");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Johanna's Argument");
        contentValues.put(COLUMN_AFFIX, "Increase the attack speed of Blessed Hammer by 100%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Justinian's Mercy");
        contentValues.put(COLUMN_AFFIX, "Blessed Hammer gains the effect of the Dominion rune.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "K'mar Tenclip");
        contentValues.put(COLUMN_AFFIX, "Strafe gains the effect of the Drifting Shadow rune.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Kassar's Retribution");
        contentValues.put(COLUMN_AFFIX, "Casting Justice increases your movement speed by 20% for 2 seconds.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Kridershot");
        contentValues.put(COLUMN_AFFIX, "Elemental Arrow now generates 4 Hatred.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Leonine Bow of Hashir");
        contentValues.put(COLUMN_AFFIX, "Bola Shot has a 20% chance on explosion to pull in all enemies within 24 yards.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Light of Grace");
        contentValues.put(COLUMN_AFFIX, "Ray of Frost now pierces.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Lion's Claw");
        contentValues.put(COLUMN_AFFIX, "Seven-Sided Strike performs an additional 7 strikes.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Mad Monarch's Scepter");
        contentValues.put(COLUMN_AFFIX, "After killing 10 enemies you release a Poison Nova that deals 1400% weapon damage as Poison to enemies within 30 yards.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Madawc's Sorrow");
        contentValues.put(COLUMN_AFFIX, "Stun enemies for 2 seconds the first time you hit them.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Maloth's Focus");
        contentValues.put(COLUMN_AFFIX, "Enemies occasionally flee at the sight of this staff.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Maximus");
        contentValues.put(COLUMN_AFFIX, "Chance on hit to summon a Demonic Slave.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Meticulous Bolts");
        contentValues.put(COLUMN_AFFIX, "Elemental Arrow - Ball Lightning now travels at 40% speed.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Mirrorball");
        contentValues.put(COLUMN_AFFIX, "Magic Missile fires 1–2 extra missiles.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Myken's Ball of Hate");
        contentValues.put(COLUMN_AFFIX, "Electrocute can chain to enemies that have already been hit.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Odyn Son");
        contentValues.put(COLUMN_AFFIX, "40% chance to Chain Lightning enemies when you hit them.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Odyssey's End");
        contentValues.put(COLUMN_AFFIX, "Enemies snared by your Entangling Shot take 20–25% increased damage from all sources.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Piro Marella");
        contentValues.put(COLUMN_AFFIX, "Reduces the Wrath cost of Shield Bash by 50%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Pus Spitter");
        contentValues.put(COLUMN_AFFIX, "50% chance to lob an acid blob when attacking.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Remorsless");
        contentValues.put(COLUMN_AFFIX, "Hammer of the Ancients has a 25–30% chance to summon an Ancient for 20 seconds.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Rhen'ho Flayer");
        contentValues.put(COLUMN_AFFIX, "Plague of Toads now seek out enemies and can explode twice.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Rimeheart");
        contentValues.put(COLUMN_AFFIX, "10% chance on hit to instantly deal 10000% weapon damage as Cold to enemies that are Frozen.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Sacred Harvester");
        contentValues.put(COLUMN_AFFIX, "Soul Harvest now stacks up to 10 times.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Salvation");
        contentValues.put(COLUMN_AFFIX, "Blocked attacks heal you and your allies for 30% of the amount blocked");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Schaefer's Hammer");
        contentValues.put(COLUMN_AFFIX, "Casting a Lightning skill charges you with Lightning, causing you to deal 850% weapon damage as Lightning every second for 5 seconds to nearby enemies.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Scourge");
        contentValues.put(COLUMN_AFFIX, "45% chance when attacking to explode with demonic fury for 2000% weapon damage as Fire.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Scrimshaw");
        contentValues.put(COLUMN_AFFIX, "Reduces the Mana cost of Zombie Charger by 50%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Serpent's Sparker");
        contentValues.put(COLUMN_AFFIX, "You may have one extra Hydra active at a time.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Sever");
        contentValues.put(COLUMN_AFFIX, "Slain enemies rest in pieces.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Shard of Hate");
        contentValues.put(COLUMN_AFFIX, "Elemental skills have a chance to trigger a powerful attack that deals 250% weapon damage:Cold skills trigger Freezing Skull, Poison skills trigger Poison Nova Lightning skills trigger Charged Bolt");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Shukrani's Triumph");
        contentValues.put(COLUMN_AFFIX, "Spirit Walk lasts until you attack or until an enemy is within 30 yards of you.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Sky Splitter");
        contentValues.put(COLUMN_AFFIX, "20% chance to Smite enemies for 600-750% weapon damage as Lightning when you hit them.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Skycutter");
        contentValues.put(COLUMN_AFFIX, "Chance to summon angelic assistance when attacking.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Skywarden");
        contentValues.put(COLUMN_AFFIX, "Every 60 seconds, gain a random Law for 60 seconds.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Slorak's Madness");
        contentValues.put(COLUMN_AFFIX, "This wand finds your death humorous.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Solanium");
        contentValues.put(COLUMN_AFFIX, "Critical Hits have a 4% chance to spawn a health globe.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Soulsmasher");
        contentValues.put(COLUMN_AFFIX, "When you kill an enemy, it explodes for 600% of your Life per Kill as damage to all enemies within 20 yards. You no longer benefit from your Life per Kill");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Spines of Seething Hatred");
        contentValues.put(COLUMN_AFFIX, "Chakram now generates 4 Hatred.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Stalgard's Decimator");
        contentValues.put(COLUMN_AFFIX, "Your melee attacks throw a piercing axe at a nearby enemy, dealing 700% weapon damage as Physical.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Starmetal Kukri");
        contentValues.put(COLUMN_AFFIX, "Reduce the cooldown of Fetish Army and Big Bad Voodoo by 1 second each time your fetishes deal damage.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "SuWong Diviner");
        contentValues.put(COLUMN_AFFIX, "Acid Cloud gains the effect of the Lob Blob Bomb rune.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Sublime Conviction");
        contentValues.put(COLUMN_AFFIX, "When you block, you have up to a 20% chance to Stun the attacker for 1.5 seconds based on your current Wrath.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Sunder");
        contentValues.put(COLUMN_AFFIX, "50% chance to sunder the ground your enemies walk on when you attack.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Swiftmount");
        contentValues.put(COLUMN_AFFIX, "Doubles the duration of Steed Charge.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Burning Axe of Sankis");
        contentValues.put(COLUMN_AFFIX, "Chance to fight through the pain when enemies hit you.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Butcher's Sickle");
        contentValues.put(COLUMN_AFFIX, "25% chance to drag enemies to you when attacking.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Dagger of Darts");
        contentValues.put(COLUMN_AFFIX, "Your Poison Darts and your Fetishes' Poison Darts now pierce.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Demon's Demise");
        contentValues.put(COLUMN_AFFIX, "Spike Trap - Sticky Trap spreads to nearby enemies when it explodes.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Final Witness");
        contentValues.put(COLUMN_AFFIX, "Shield Glare now hits all enemies around you.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Fist of Az'Turrasq");
        contentValues.put(COLUMN_AFFIX, "Exploding Palm's on-death explosion damage is increased by 100%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Flow of Eternity");
        contentValues.put(COLUMN_AFFIX, "Reduces the cooldown of Seven-Sided Strike by 60%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Furnace");
        contentValues.put(COLUMN_AFFIX, "Increases damage against elites by 50%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Gavel of Judgment");
        contentValues.put(COLUMN_AFFIX, "Hammer of the Ancients returns 20–25 Fury if it hits 3 or less enemies.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Gidbinn");
        contentValues.put(COLUMN_AFFIX, "Chance to summon a Fetish when attacking.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Grand Vizier");
        contentValues.put(COLUMN_AFFIX, "Reduces the Arcane Power cost of Meteor by 50%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Mortal Drama");
        contentValues.put(COLUMN_AFFIX, "Double the number of Bombardment impacts.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Ninth Cirri Satchel");
        contentValues.put(COLUMN_AFFIX, "Hungering Arrow has 25% additional chance to pierce.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Oculus");
        contentValues.put(COLUMN_AFFIX, "Taking damage has up to a 15–20% chance to reset the cooldown on Teleport.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Raven's Wing");
        contentValues.put(COLUMN_AFFIX, "A raven flies to your side.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Smoldering Core");
        contentValues.put(COLUMN_AFFIX, "Lesser enemies are now lured to your Meteor impact areas.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Spider Queen's Grasp");
        contentValues.put(COLUMN_AFFIX, "Corpse Spiders releases a web on impact that Slows enemies by 60–80%.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Tormentor");
        contentValues.put(COLUMN_AFFIX, "Chance to charm enemies when you hit them.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Thunderfury, Blessed Blade of the Windseeker");
        contentValues.put(COLUMN_AFFIX, "Chance on hit to blast your enemy with Lightning, dealing 372% weapon damage as Lightning and then jumping to additional nearby enemies. Each enemy hit has their attack speed and movement speed reduced by 30% for 3 seconds. Jumps up to 5 targets.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Triumvirate");
        contentValues.put(COLUMN_AFFIX, "Your Signature Spells increase the damage of Arcane Orb by 75–100% for 6 seconds, stacking up to 3 times.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Uhkapian Serpent");
        contentValues.put(COLUMN_AFFIX, "25–30% of the damage you take is redirected to your Zombie Dogs.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Unrelenting Phalanx");
        contentValues.put(COLUMN_AFFIX, "Phalanx now casts twice.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Valla's Bequest");
        contentValues.put(COLUMN_AFFIX, "Strafe projectiles pierce.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Valthek's Rebuke");
        contentValues.put(COLUMN_AFFIX, "Energy Twister now travels in a straight path.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Vengeful Wind");
        contentValues.put(COLUMN_AFFIX, "Increases the maximum stack count of Sweeping Wind by 3.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Vigilance");
        contentValues.put(COLUMN_AFFIX, "Getting hit has a chance to automatically cast Inner Sanctuary.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Vo'Toyias Spiker");
        contentValues.put(COLUMN_AFFIX, "Enemies affected by Provoke take double damage from Thorns.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Wall of Man");
        contentValues.put(COLUMN_AFFIX, "30% chance to be protected by a shield of bones when you are hit.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Wand of Woh");
        contentValues.put(COLUMN_AFFIX, "3 additional Explosive Blasts are triggered after casting Explosive Blast.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Warstaff of General Quang");
        contentValues.put(COLUMN_AFFIX, "Tempest Rush gains the effect of the Tailwind rune.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Winter Flurry");
        contentValues.put(COLUMN_AFFIX, "Enemies killed by Cold damage have a 15–20% chance to release a Frost Nova.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Wizardspike");
        contentValues.put(COLUMN_AFFIX, "Performing an attack has a 25% chance to hurl a Frozen Orb.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Wojahnni Assaulter");
        contentValues.put(COLUMN_AFFIX, "Rapid Fire deals 40% more damage for every second that you channel. Stacks up to 4 times.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Wormwood");
        contentValues.put(COLUMN_AFFIX, "Locust Swarm continuously plagues enemies around you.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Yang's Recurve");
        contentValues.put(COLUMN_AFFIX, "Multishot attacks 40% faster.");
        contentValues.put(COLUMN_TYPE, "weapon");

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public void initializeArmors() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, "Ancient Parthan Defenders");
        contentValues.put(COLUMN_AFFIX, "Each stunned enemy within 25 yards reduces your damage taken by 12%.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Andariel's Visage");
        contentValues.put(COLUMN_AFFIX, "Attacks release a Poison Nova that deals 450% weapon damage as Poison to enemies within 10 yards.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Angel Hair Braid");
        contentValues.put(COLUMN_AFFIX, "Punish gains the effect of every rune.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Archmage's Vicalyke");
        contentValues.put(COLUMN_AFFIX, "Your Mirror Images have a chance to multiply when killed by enemies.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Armor of the Kind Regent");
        contentValues.put(COLUMN_AFFIX, "Smite will now also be cast at a second nearby enemy.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Beckon Sail");
        contentValues.put(COLUMN_AFFIX, "When receiving fatal damage, you instead automatically cast Smoke Screen and are healed to 25% Life. This effect may occur once every 120 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Belt of Transcendence");
        contentValues.put(COLUMN_AFFIX, "Summon a Fetish Sycophant when you hit with a Mana spender.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Belt of the Trove");
        contentValues.put(COLUMN_AFFIX, "Every 6–8 seconds, call down Bombardment on a random nearby enemy.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Binding of the Lost");
        contentValues.put(COLUMN_AFFIX, "Each hit with Seven-Sided Strike grants 3.5% damage reduction for 7 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Blackfeather");
        contentValues.put(COLUMN_AFFIX, "Dodging or getting hit by a ranged attack automatically shoots a homing rocket back at the attacker for 800% weapon damage as Physical.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Blessed of Haull");
        contentValues.put(COLUMN_AFFIX, "Justice spawns a Blessed Hammer when it hits an enemy.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Boots of Disregard");
        contentValues.put(COLUMN_AFFIX, "Gain 10000 Life Regeneration per second for each second you stand still. This effect stacks up to 8 times");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Bracers of Destruction");
        contentValues.put(COLUMN_AFFIX, "Seismic Slam deals 400% increased damage to the first two enemies it hits.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Bracers of the First Men");
        contentValues.put(COLUMN_AFFIX, "Hammer of the Ancients attacks 50% faster and deals 200% increased damage.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Broken Crown");
        contentValues.put(COLUMN_AFFIX, "Whenever a gem drops, a gem of the type socketed into this item also drops.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Cape of the Dark Night");
        contentValues.put(COLUMN_AFFIX, "Automatically drop Caltrops when you are hit. This effect may only occur once every 6 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Carnevil");
        contentValues.put(COLUMN_AFFIX, "Your Fetishes shoot a Poison Dart every time you do.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Chaigmail");
        contentValues.put(COLUMN_AFFIX, "After earning a survival bonus, quickly heal to full Life.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Chilanik's Chain");
        contentValues.put(COLUMN_AFFIX, "Using War Cry increases the movement speed for you and all allies affected by 40% for 10 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Cindercoat");
        contentValues.put(COLUMN_AFFIX, "Reduces the resource cost of Fire skills by 30%.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Cloak of Deception");
        contentValues.put(COLUMN_AFFIX, "Enemy missiles sometimes pass through you harmlessly.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Coils of the First Spider");
        contentValues.put(COLUMN_AFFIX, "While channeling Firebats, gain 80,000 Life per Hit.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Cord of the Sherma");
        contentValues.put(COLUMN_AFFIX, "Chance on hit to create a chaos field that Blinds and Slows enemies inside for 4 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Crashing Rain");
        contentValues.put(COLUMN_AFFIX, "Rain of Vengeance also summons a crashing beast that deals 4000% weapon damage.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Crown of the Primus");
        contentValues.put(COLUMN_AFFIX, "Slow Time gains the effect of every rune.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Custerian Wristguards");
        contentValues.put(COLUMN_AFFIX, "Picking up gold grants experience.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Dark Mage's Shade");
        contentValues.put(COLUMN_AFFIX, "Automatically cast Diamond Skin when you fall below 35% Life. This effect may occur once every 15 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Death Watch Mantle");
        contentValues.put(COLUMN_AFFIX, "35% chance to explode in a fan of knives for 750-950% weapon damage when hit.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Death's Bargain");
        contentValues.put(COLUMN_AFFIX, "Gain an aura of death that deals 1000% of your Life per Second to enemies within 20 yards. You no longer regenerate Life");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Deathseer's Cowl");
        contentValues.put(COLUMN_AFFIX, "20% chance on being hit by an Undead enemy to charm it for 2 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Depth Diggers");
        contentValues.put(COLUMN_AFFIX, "Primary skills that generate resource deal 100% additional damage.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Drakon's Lesson");
        contentValues.put(COLUMN_AFFIX, "When your Shield Bash hits 3 or less enemies, its damage is increased by 200% and 25% of its Wrath Cost is refunded.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Dread Iron");
        contentValues.put(COLUMN_AFFIX, "Ground Stomp causes an Avalanche.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Eye of Peshkov");
        contentValues.put(COLUMN_AFFIX, "Reduce the cooldown of Breath of Heaven by 50%.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Fazula's Improbable Chain");
        contentValues.put(COLUMN_AFFIX, "Archon stacks also increase your Attack Speed, Armor and Resistances by 1%.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Fire Walkers");
        contentValues.put(COLUMN_AFFIX, "Burn the ground you walk on, dealing 400% weapon damage each second.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Frostburn");
        contentValues.put(COLUMN_AFFIX, "Your Cold damage has up to a 45% chance to Freeze enemies.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Fury of the Ancients");
        contentValues.put(COLUMN_AFFIX, "Call of the Ancients gains the effect of the Ancients' Fury rune.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Gabriel's Vambraces");
        contentValues.put(COLUMN_AFFIX, "When your Blessed Hammer hits 3 or fewer enemies, 100% of its Wrath Cost is refunded.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Gladiator Gauntlets");
        contentValues.put(COLUMN_AFFIX, "After earning a massacre bonus, gold rains from sky.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Gloves of Worship");
        contentValues.put(COLUMN_AFFIX, "Shrine effects last for 10 minutes.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Goldskin");
        contentValues.put(COLUMN_AFFIX, "Chance for enemies to drop gold when you hit them.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Goldwrap");
        contentValues.put(COLUMN_AFFIX, "On gold pickup: Gain armor for 5 seconds equal to the amount picked up.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Gungdo Gear");
        contentValues.put(COLUMN_AFFIX, "Exploding Palm's on-death explosion applies Exploding Palm.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Gyana Na Kashu");
        contentValues.put(COLUMN_AFFIX, "Lashing Tail Kick releases a piercing fireball that deals 700% weapon damage as Fire to enemies within 10 yards on impact.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Harrington Waistguard");
        contentValues.put(COLUMN_AFFIX, "Opening a chest grants 135% increased damage for 10 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Haunting Girdle");
        contentValues.put(COLUMN_AFFIX, "Haunt releases 1 extra spirit.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Hexing Pants of Mr. Yan");
        contentValues.put(COLUMN_AFFIX, "Your resource generation and damage is increased by 25% while moving and decreased by 20% while standing still.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Homing Pads");
        contentValues.put(COLUMN_AFFIX, "Your Town Portal is no longer interrupted by taking damage. While casting Town Portal you gain a protective bubble that reduces damage taken by 65%.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Hunter's Wrath");
        contentValues.put(COLUMN_AFFIX, "Your Hatred generators attack 30% faster and deal 40% increased damage.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Hwoj Wrap");
        contentValues.put(COLUMN_AFFIX, "Locust Swarm also Slows enemies by 80%.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Ice Climbers");
        contentValues.put(COLUMN_AFFIX, "Gain immunity to Freeze and Immobilize effects.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Illusionary Boots");
        contentValues.put(COLUMN_AFFIX, "You may move unhindered through enemies.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Insatiable Belt");
        contentValues.put(COLUMN_AFFIX, "Picking up a Health Globe increases your maximum Life by 5% for 15 seconds, stacking up to 5 times");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Irontoe Mudsputters");
        contentValues.put(COLUMN_AFFIX, "Gain up to 30% increased movement speed based on amount of Life missing.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Jang's Envelopment");
        contentValues.put(COLUMN_AFFIX, "Enemies damaged by Black Hole are also slowed by 80% for 3 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Jeram's Bracers");
        contentValues.put(COLUMN_AFFIX, "Wall of Death can be cast up to twice again within 2 seconds before the cooldown begins.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Kekegi's Unbreakable Spirit");
        contentValues.put(COLUMN_AFFIX, "Damaging enemies has a chance to grant you an effect that removes the Spirit cost of your abilities for 4 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Krelm's Buff Belt");
        contentValues.put(COLUMN_AFFIX, "Gain 25% run speed. This effect is lost for 5 seconds after taking damage.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Krelm's Buff Bracers");
        contentValues.put(COLUMN_AFFIX, "You are immune to Knockback and Stun effects.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Lamentation");
        contentValues.put(COLUMN_AFFIX, "Rend can now stack up to 2 times on an enemy.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Leorics Crown");
        contentValues.put(COLUMN_AFFIX, "Increase the effect of any gem socketed into this item by 100%.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Lut Socks");
        contentValues.put(COLUMN_AFFIX, "Leap can be cast again within 2 seconds before the cooldown begins.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Madstone");
        contentValues.put(COLUMN_AFFIX, "Your Seven-Sided Strike applies Exploding Palm.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Mask of Jeram");
        contentValues.put(COLUMN_AFFIX, "Pets deal 75–100% more damage.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Nemesis Bracers");
        contentValues.put(COLUMN_AFFIX, "Shrines and Pylons will spawn an enemy champion.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Nilfur's Boast");
        contentValues.put(COLUMN_AFFIX, "Increase the damage of Meteor by 100%. When your Meteor hits 3 or less enemies, the damage is increased by 200%.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Omnislash");
        contentValues.put(COLUMN_AFFIX, "Slash attacks in all directions.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Omryn's Chain");
        contentValues.put(COLUMN_AFFIX, "Drop Caltrops when using Vault.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Pauldrons of the Skeleton King");
        contentValues.put(COLUMN_AFFIX, "When receiving fatal damage, there is a chance that you are instead restored to 25% of maximum Life and cause nearby enemies to flee in fear.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Pox Faulds");
        contentValues.put(COLUMN_AFFIX, "When 3 or more enemies are within 12 yards, you release a vile stench that deals 550%weapon damage as Poison every second for 5 seconds to enemies within 15 yards.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Pride of Cassius");
        contentValues.put(COLUMN_AFFIX, "Increases the duration of Ignore Pain by 6 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Pride's Fall");
        contentValues.put(COLUMN_AFFIX, "Your resource costs are reduced by 30% after not taking damage for 5 seconds");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Promise of Glory");
        contentValues.put(COLUMN_AFFIX, "6% chance to spawn a Nephalem Glory globe when you Blind an enemy.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Quetzalcoatl");
        contentValues.put(COLUMN_AFFIX, "Locust Swarm and Haunt now deal their damage in half of the normal duration.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Ranslor's Folly");
        contentValues.put(COLUMN_AFFIX, "Energy Twister periodically pulls in lesser enemies within 30 yards.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Razor Strop");
        contentValues.put(COLUMN_AFFIX, "Picking up a Health Globe releases an explosion that deals 400% weapon damage as Fire to enemies within 20 yards.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Reaper's Wraps");
        contentValues.put(COLUMN_AFFIX, "Health globes restore 30% of your primary resource.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Sacred Harness");
        contentValues.put(COLUMN_AFFIX, "Judgment is cast at your landing location when casting Falling Sword.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Sanguinary Vambracers");
        contentValues.put(COLUMN_AFFIX, "Chance on being hit to deal 1000% of your Thorns damage to nearby enemies.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Sash of Knives");
        contentValues.put(COLUMN_AFFIX, "With every attack, you throw a dagger at a nearby enemy for 650% weapon damage as Physical.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Sebor's Nightmare");
        contentValues.put(COLUMN_AFFIX, "Haunt is cast on all nearby enemies when you open a chest.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Shi Mizu's Haori");
        contentValues.put(COLUMN_AFFIX, "While below 25% Life, all attacks are guaranteed Critical Hits.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Skull of Resonance");
        contentValues.put(COLUMN_AFFIX, "Threatening Shout has a chance to Charm enemies and cause them to join your side.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Spaulders of Zakara");
        contentValues.put(COLUMN_AFFIX, "Your items become indestructible.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Spirit Guards");
        contentValues.put(COLUMN_AFFIX, "Your Spirit Generators reduce your damage taken by 40% for 3 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "St. Archew's Gage");
        contentValues.put(COLUMN_AFFIX, "The first time an elite pack damages you, gain an absorb shield equal to 150% of your maximum Life for 10 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Storm Crow");
        contentValues.put(COLUMN_AFFIX, "40% chance to cast a fiery ball when attacking.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Strongarm Bracers");
        contentValues.put(COLUMN_AFFIX, "Enemies hit by knockbacks suffer 30% more damage for 5 seconds when they land.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Tasker and Theo");
        contentValues.put(COLUMN_AFFIX, "Increase attack speed of your pets by 50%.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Cloak of the Garwulf");
        contentValues.put(COLUMN_AFFIX, "Companion - Wolf Companion now summons 3 wolves.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Crudest Boots");
        contentValues.put(COLUMN_AFFIX, "Mystic Ally summons two Mystic Allies that fight by your side.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Grin Reaper");
        contentValues.put(COLUMN_AFFIX, "Chance to summon horrific Mimics when attacking.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Laws of Seph");
        contentValues.put(COLUMN_AFFIX, "Using Blinding Flash restores 165 Spirit.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Magistrate");
        contentValues.put(COLUMN_AFFIX, "Frost Hydra now periodically casts Frost Nova.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Mind's Eye");
        contentValues.put(COLUMN_AFFIX, "Inner Sanctuary increases Spirit Regeneration per second by 15.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Swami");
        contentValues.put(COLUMN_AFFIX, "The bonuses from Archon stacks now last for 15-20 seconds after Archon expires.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "The Undisputed Champion");
        contentValues.put(COLUMN_AFFIX, "Frenzy gains the effect of every rune.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Thundergod's Vigor");
        contentValues.put(COLUMN_AFFIX, "Blocking, dodging or being hit causes you to discharge bolts of electricity that deal 130% weapon damage as Lightning.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Tiklandian Visage");
        contentValues.put(COLUMN_AFFIX, "Horrify causes you to Fear and Root enemies around you for 6–8 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Trag'Oul Coils");
        contentValues.put(COLUMN_AFFIX, "Healing wells replenish all resources and reduce all cooldowns by 60 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Tzo Krin's Gaze");
        contentValues.put(COLUMN_AFFIX, "Wave of Light is now cast at your enemy.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Velvet Camaral");
        contentValues.put(COLUMN_AFFIX, "Double the number of enemies your Electrocute jumps to.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Vile Ward");
        contentValues.put(COLUMN_AFFIX, "Furious Charge deals 35% increased damage for every enemy hit while charging.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Visage of Giyua");
        contentValues.put(COLUMN_AFFIX, "Summon a Fetish Army after you kill 2 Elites.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Warzechian Armguards");
        contentValues.put(COLUMN_AFFIX, "Every time you destroy a wreckable object, you gain a short burst of speed.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
        contentValues.put(COLUMN_NAME, "Wraps of Clarity");
        contentValues.put(COLUMN_AFFIX, "Your Hatred Generators reduce your damage taken by 30-35% for 5 seconds.");
        contentValues.put(COLUMN_TYPE, "armor");

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }


}
