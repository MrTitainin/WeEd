package wesnoth.editor;

import java.util.HashMap;
import java.util.LinkedList;

public class StructureGenerator{
	protected static HashMap<String, KeyData[]> constructAttrTable(){
	    HashMap<String,KeyData[]> result = new HashMap<String,KeyData[]>();
	
	    KeyData[] keyData = new KeyData[0];
	
	    LinkedList<KeyData> movement_costs = new LinkedList<KeyData>();
	    movement_costs.add(new KeyData(null, KeyData.INTEGER));
	    result.put("[movement_costs]", movement_costs.toArray(keyData));
	
	    LinkedList<KeyData> vision_costs = new LinkedList<KeyData>();
	    vision_costs.add(new KeyData(null, KeyData.INTEGER));
	    result.put("[vision_costs]", vision_costs.toArray(keyData));
	
	    LinkedList<KeyData> resistance = new LinkedList<KeyData>();
	    resistance.add(new KeyData(null, KeyData.INTEGER));
	    result.put("[resistance]", resistance.toArray(keyData));
	
	    LinkedList<KeyData> defense = new LinkedList<KeyData>();
	    defense.add(new KeyData(null, KeyData.INTEGER));
	    result.put("[defense]", defense.toArray(keyData));
	
	    LinkedList<KeyData> filter = new LinkedList<KeyData>();
	    filter.add(new KeyData("id", (byte)1));
	    filter.add(new KeyData("speaker", (byte)1));
	    filter.add(new KeyData("type", (byte)1));
	    filter.add(new KeyData("race", (byte)1));
	    filter.add(new KeyData("ability", (byte)1));
	    filter.add(new KeyData("side", (byte)10));
	    filter.add(new KeyData("has_weapon", (byte)1));
	    filter.add(new KeyData("canrecruit", (byte)0));
	    filter.add(new KeyData("gender", (byte)1));
	    filter.add(new KeyData("role", (byte)1));
	    filter.add(new KeyData("level", (byte)10));
	    filter.add(new KeyData("defense", (byte)10));
	    filter.add(new KeyData("movement_cost", (byte)10));
	    filter.add(new KeyData("x,y", (byte)22));
	    filter.add(new KeyData("find_in", (byte)1));
	    filter.add(new KeyData("formula", (byte)20));
	    filter.add(new KeyData("lua_function", (byte)20));
	    result.put("[filter]", filter.toArray(keyData));
	    result.put("[filter_recall]", filter.toArray(keyData));
	    result.put("[filter_second]", filter.toArray(keyData));
	
	    LinkedList<KeyData> unit_type = new LinkedList<KeyData>();
	    unit_type.add(new KeyData("id", KeyData.KEY));
	    unit_type.add(new KeyData("name", KeyData.STRING, true));
	    unit_type.add(new KeyData("race", KeyData.KEY));
	    unit_type.add(new KeyData("image", KeyData.FILE));
	    unit_type.add(new KeyData("profile", KeyData.FILE));
	    unit_type.add(new KeyData("small_profile", KeyData.FILE));
	    unit_type.add(new KeyData("ellipse", KeyData.FILE));
	    unit_type.add(new KeyData("flag_rgb", KeyData.KEY));
	    unit_type.add(new KeyData("hitpoints", KeyData.INTEGER));
	    unit_type.add(new KeyData("movement_type", KeyData.KEY));
	    unit_type.add(new KeyData("movement", KeyData.INTEGER));
	    unit_type.add(new KeyData("experience", KeyData.INTEGER));
	    unit_type.add(new KeyData("level", KeyData.INTEGER));
	    LinkedList<String> values = new LinkedList<String>();
	    values.add("lawful");
	    values.add("chaotic");
	    values.add("liminal");
	    unit_type.add(new KeyData("alignment", KeyData.OPTIONS, values, "neutral"));
	    unit_type.add(new KeyData("advances_to", KeyData.KEY));
	    unit_type.add(new KeyData("cost", KeyData.INTEGER));
	    unit_type.add(new KeyData("usage", KeyData.KEY));
	    values = new LinkedList<String>();
	    values.add("female");
	    unit_type.add(new KeyData("gender", KeyData.OPTIONS, values, "male"));
	    unit_type.add(new KeyData("description", KeyData.LONG, true));
	    unit_type.add(new KeyData("die_sound", KeyData.FILE));
	    unit_type.add(new KeyData("attacks", KeyData.INTEGER));
	    unit_type.add(new KeyData("do_not_list", KeyData.BOOLEAN));
	    unit_type.add(new KeyData("hide_help", KeyData.BOOLEAN, "false"));
	    unit_type.add(new KeyData("ignore_race_traits", KeyData.BOOLEAN, "false"));
	    unit_type.add(new KeyData("image_icon", KeyData.FILE));
	    unit_type.add(new KeyData("num_traits", KeyData.INTEGER));
	    unit_type.add(new KeyData("vision", KeyData.INTEGER));
	    unit_type.add(new KeyData("zoc", KeyData.BOOLEAN));
	    result.put("[unit_type]", unit_type.toArray(keyData));
	    result.put("[male]", unit_type.toArray(keyData));
	    result.put("[female]", unit_type.toArray(keyData));
	
	    LinkedList<KeyData> attack = new LinkedList<KeyData>();
	    attack.add(new KeyData("description", KeyData.STRING, true));
	    attack.add(new KeyData("name", KeyData.KEY));
	    attack.add(new KeyData("type", KeyData.KEY));
	    attack.add(new KeyData("icon", KeyData.FILE));
	    attack.add(new KeyData("range", KeyData.KEY));
	    attack.add(new KeyData("damage", KeyData.INTEGER));
	    attack.add(new KeyData("number", KeyData.INTEGER));
	    attack.add(new KeyData("movement_used", KeyData.INTEGER, "-1"));
	    attack.add(new KeyData("attack_weight", KeyData.INTEGER, "1"));
	    attack.add(new KeyData("defense_weight", KeyData.INTEGER, "1"));
	    result.put("[attack]", attack.toArray(keyData));
	
	    LinkedList<KeyData> special = new LinkedList<KeyData>();
	    special.add(new KeyData("#textdomain", KeyData.KEY));
	    special.add(new KeyData("code", KeyData.LONG));
	    result.put("*special", special.toArray(keyData));
	
	    LinkedList<KeyData> frame = new LinkedList<KeyData>();
	    frame.add(new KeyData("duration", KeyData.INTEGER));
	    frame.add(new KeyData("begin", KeyData.INTEGER));
	    frame.add(new KeyData("end", KeyData.INTEGER));
	    frame.add(new KeyData("image", KeyData.FILE));
	    frame.add(new KeyData("image_diagonal", KeyData.FILE));
	    frame.add(new KeyData("image_mod", KeyData.STRING));
	    frame.add(new KeyData("sound", KeyData.FILE));
	    frame.add(new KeyData("halo", KeyData.KEY));
	    frame.add(new KeyData("halo_x", KeyData.KEY));
	    frame.add(new KeyData("halo_y", KeyData.KEY));
	    frame.add(new KeyData("halo_mod", KeyData.STRING));
	    frame.add(new KeyData("alpha", KeyData.KEY));
	    frame.add(new KeyData("offset", KeyData.KEY));
	    frame.add(new KeyData("blend_color", KeyData.KEY));
	    frame.add(new KeyData("blend_ratio", KeyData.KEY));
	    frame.add(new KeyData("text", KeyData.STRING,true));
	    frame.add(new KeyData("text_color", KeyData.KEY));
	    frame.add(new KeyData("submerge", KeyData.KEY));
	    frame.add(new KeyData("x", KeyData.KEY));
	    frame.add(new KeyData("y", KeyData.KEY));
	    frame.add(new KeyData("directional_x", KeyData.KEY));
	    frame.add(new KeyData("directional_y", KeyData.KEY));
	    frame.add(new KeyData("layer", KeyData.KEY));
	    frame.add(new KeyData("auto_hflip", KeyData.BOOLEAN));
	    frame.add(new KeyData("auto_vflip", KeyData.BOOLEAN));
	    frame.add(new KeyData("primary", KeyData.BOOLEAN));
	    result.put("[frame]", frame.toArray(keyData));
	    result.put("[missile_frame]", frame.toArray(keyData));
	
	    LinkedList<KeyData> advance_from = new LinkedList<KeyData>();
	    advance_from.add(new KeyData("unit", KeyData.KEY));
	    advance_from.add(new KeyData("experience", KeyData.INTEGER));
	    result.put("[advance_from]", attack.toArray(keyData));
	
	    LinkedList<KeyData> base_unit = new LinkedList<KeyData>();
	    base_unit.add(new KeyData("id", KeyData.KEY));
	    result.put("[base_unit]", attack.toArray(keyData));
	
	    LinkedList<KeyData> portrait = new LinkedList<KeyData>();
	    portrait.add(new KeyData("size", KeyData.KEY));
	    values.clear();
	    values.add("left");
	    values.add("right");
	    portrait.add(new KeyData("size", KeyData.OPTIONS, values));
	    portrait.add(new KeyData("mirror", KeyData.BOOLEAN));
	    portrait.add(new KeyData("image", KeyData.FILE));
	    result.put("[portrait]", attack.toArray(keyData));
	    
	    LinkedList<KeyData> status = new LinkedList<KeyData>();
	    status.add(new KeyData("poisoned", KeyData.BOOLEAN));
	    status.add(new KeyData("petrified", KeyData.BOOLEAN));
	    status.add(new KeyData("slowed", KeyData.BOOLEAN));
	    status.add(new KeyData("uncovered", KeyData.BOOLEAN));
	    status.add(new KeyData("guardian", KeyData.BOOLEAN));
	    status.add(new KeyData("unhealable", KeyData.BOOLEAN));
	    result.put("[status]", status.toArray(keyData));
	    
	    LinkedList<KeyData>  unit = new LinkedList<KeyData>();
	    unit.add(new KeyData("General",KeyData.SEPARATOR));
	    unit.add(new KeyData("side",KeyData.INTEGER));
	    unit.add(new KeyData("type",KeyData.KEY));
	    unit.add(new KeyData("name",KeyData.STRING,true));
	    unit.add(new KeyData("id",KeyData.KEY));
	    values.clear();
	    values.add("female");
	    unit.add(new KeyData("gender",KeyData.OPTIONS,values,"male"));
	    unit.add(new KeyData("variation",KeyData.KEY));
	    values.clear();
	    values.add("leader");
	    values.add("recall");
	    values.add("map_overwrite");
	    values.add("map_passable");
	    values.add("leader_passable");
	    unit.add(new KeyData("placement",KeyData.OPTIONS,values,"map"));
	    unit.add(new KeyData("x",KeyData.INTEGER));
	    unit.add(new KeyData("y",KeyData.INTEGER));
	    
	    unit.add(new KeyData("Images",KeyData.SEPARATOR));
	    values.clear();
	    values.add("s");
	    values.add("sw");
	    values.add("nw");
	    values.add("n");
	    values.add("ne");
	    unit.add(new KeyData("facing",KeyData.OPTIONS,values,"se"));
	    unit.add(new KeyData("profile",KeyData.FILE));
	    unit.add(new KeyData("small_profile",KeyData.FILE));
	    unit.add(new KeyData("overlays",KeyData.STRING));
	    
	    unit.add(new KeyData("Leader",KeyData.SEPARATOR));
	    unit.add(new KeyData("canrecruit",KeyData.BOOLEAN));
	    unit.add(new KeyData("extra_recruit",KeyData.STRING));
	    
	    unit.add(new KeyData("Starting specials",KeyData.SEPARATOR));
	    unit.add(new KeyData("hitpoints",KeyData.INTEGER));
	    unit.add(new KeyData("experience",KeyData.INTEGER));
	    unit.add(new KeyData("moves",KeyData.INTEGER));
	    unit.add(new KeyData("attacks_left",KeyData.INTEGER));
	    unit.add(new KeyData("resting",KeyData.BOOLEAN));
	    values = new LinkedList<String>();
	    values.add("loyal");
	    values.add("free");
	    unit.add(new KeyData("upkeep",KeyData.OPTIONS,values,"full"));
	    
	    unit.add(new KeyData("AI",KeyData.SEPARATOR));
	    unit.add(new KeyData("goto_x,goto_y",KeyData.STRING));
	    unit.add(new KeyData("ai_special",KeyData.KEY));
	    
	    unit.add(new KeyData("Overrides",KeyData.SEPARATOR));
	    unit.add(new KeyData("traits_descriptions",KeyData.STRING));
	    unit.add(new KeyData("unit_description",KeyData.STRING));
	    unit.add(new KeyData("max_attacks",KeyData.INTEGER));
	    unit.add(new KeyData("max_experience",KeyData.INTEGER));
	    unit.add(new KeyData("max_hitpoints",KeyData.INTEGER));
	    unit.add(new KeyData("max_moves",KeyData.INTEGER));
	    
	    unit.add(new KeyData("Other",KeyData.SEPARATOR));
	    unit.add(new KeyData("to_variable",KeyData.KEY));
	    unit.add(new KeyData("generate_name",KeyData.BOOLEAN));
	    unit.add(new KeyData("unrenamable",KeyData.BOOLEAN));
	    unit.add(new KeyData("random_traits",KeyData.BOOLEAN));
	    unit.add(new KeyData("random_gender",KeyData.BOOLEAN));
	    unit.add(new KeyData("role",KeyData.KEY));
	    unit.add(new KeyData("animate",KeyData.BOOLEAN));
	    unit.add(new KeyData("hp_bar_scalling",KeyData.STRING));
	    result.put("[unit]", unit.toArray(keyData));
	    
	    LinkedList<KeyData> side = new LinkedList<KeyData>();
	    side.add(new KeyData("General",KeyData.SEPARATOR));
	    side.add(new KeyData("side", KeyData.INTEGER));
	    values.clear();
	    values.add("ai");
	    values.add("null");
	    values.add("1");
	    values.add("2");
	    values.add("3");
	    values.add("4");
	    values.add("5");
	    values.add("6");
	    values.add("7");
	    values.add("8");
	    values.add("9");
	    side.add(new KeyData("controller", KeyData.OPTIONS,values,"human"));
	    side.add(new KeyData("no_leader", KeyData.BOOLEAN));
	    side.add(new KeyData("gold", KeyData.INTEGER));
	    side.add(new KeyData("income", KeyData.INTEGER));
	    side.add(new KeyData("recruit", KeyData.KEY));
	    side.add(new KeyData("hidden", KeyData.BOOLEAN));
	    side.add(new KeyData("fog", KeyData.BOOLEAN));
	    side.add(new KeyData("fog_data", KeyData.STRING));
	    side.add(new KeyData("shroud", KeyData.BOOLEAN));
	    side.add(new KeyData("shroud_data", KeyData.STRING));
	    side.add(new KeyData("persistent", KeyData.BOOLEAN));
	    side.add(new KeyData("save_id", KeyData.KEY));
	    side.add(new KeyData("team_name", KeyData.KEY));
	    side.add(new KeyData("user_team_name", KeyData.STRING,true));
	    side.add(new KeyData("current_player", KeyData.STRING));
	    side.add(new KeyData("color", KeyData.KEY));
	    side.add(new KeyData("flag", KeyData.STRING));
	    side.add(new KeyData("flag_icon", KeyData.FILE));
	    side.add(new KeyData("village_gold", KeyData.INTEGER));
	    side.add(new KeyData("village_support", KeyData.INTEGER));
	    side.add(new KeyData("recall_cost", KeyData.INTEGER));
	    side.add(new KeyData("share_maps", KeyData.BOOLEAN));
	    side.add(new KeyData("share_viev", KeyData.BOOLEAN));
	    side.add(new KeyData("scroll_to_leader", KeyData.BOOLEAN));
	    side.add(new KeyData("supress_end_turn_confirmation", KeyData.BOOLEAN));
	    values = new LinkedList<String>();
	    values.add("no_units_left");
	    values.add("never");
	    values.add("always");
	    side.add(new KeyData("defead_condition", KeyData.OPTIONS,values,"no_leader_left"));
	    
	    side.add(new KeyData("Multiplayer options", KeyData.SEPARATOR));
	    side.add(new KeyData("allow_player", KeyData.BOOLEAN));
	    side.add(new KeyData("disallow_observers", KeyData.BOOLEAN));
	    side.add(new KeyData("choose_random", KeyData.BOOLEAN));
	    side.add(new KeyData("force_lock_settings", KeyData.BOOLEAN));
	    side.add(new KeyData("controller_lock", KeyData.BOOLEAN));
	    side.add(new KeyData("team_lock", KeyData.BOOLEAN));
	    side.add(new KeyData("color_lock", KeyData.BOOLEAN));
	    side.add(new KeyData("gold_lock", KeyData.BOOLEAN));
	    side.add(new KeyData("income_lock", KeyData.BOOLEAN));
	    side.add(new KeyData("faction_lock", KeyData.BOOLEAN));
	    side.add(new KeyData("leader_lock", KeyData.BOOLEAN));
	    side.add(new KeyData("faction", KeyData.KEY));
	    side.add(new KeyData("Leader define",KeyData.SEPARATOR));
	    if(WeEd.showAllUnitOptions){
	    	for(int i=0;i<unit.size();i++){
	    		KeyData actualkeydata = unit.get(i);
	    		if(actualkeydata.getKey().equals("side")
	    				||(actualkeydata.getKey().equals("General"))
	    				||(actualkeydata.getKey().equals("Images"))
	    				||(actualkeydata.getKey().equals("Leader"))
	    				||(actualkeydata.getKey().equals("Starting specials"))
	    				||(actualkeydata.getKey().equals("AI"))
	    				||(actualkeydata.getKey().equals("Overrides"))
	    				||(actualkeydata.getKey().equals("Other"))){
	    			unit.remove(actualkeydata);
	    		}
	    	}
	        side.addAll(unit);
	    }
	    else{
	
	        side.add(new KeyData("canrecruit",KeyData.BOOLEAN));
	        side.add(new KeyData("extra_recruit",KeyData.STRING));
	        side.add(new KeyData("unrenamable",KeyData.BOOLEAN));
	        side.add(new KeyData("type",KeyData.KEY));
	        side.add(new KeyData("name",KeyData.STRING,true));
	        side.add(new KeyData("id",KeyData.KEY));
	        values = new LinkedList<String>();
	        values.add("female");
	        side.add(new KeyData("gender",KeyData.OPTIONS,values,"male"));
	        side.add(new KeyData("variation",KeyData.KEY));
	    }
	    result.put("[side]", side.toArray(keyData));
	    
	    
	    LinkedList<KeyData> message = new LinkedList<KeyData>();
	    message.add(new KeyData("General", KeyData.SEPARATOR));
	    message.add(new KeyData("speaker",KeyData.KEY));
	    message.add(new KeyData("message",KeyData.STRING,true));
	    message.add(new KeyData("image",KeyData.FILE));
	    message.add(new KeyData("caption",KeyData.STRING,true));
	    
	    message.add(new KeyData("Advanced", KeyData.SEPARATOR));
	    message.add(new KeyData("mirror",KeyData.BOOLEAN));
	    message.add(new KeyData("male_message",KeyData.STRING,true));
	    message.add(new KeyData("female_message",KeyData.STRING,true));
	    message.add(new KeyData("second_image",KeyData.FILE));
	    values.clear();
	    values.add("right");
	    message.add(new KeyData("image_pos",KeyData.OPTIONS,values,"left"));
	    message.add(new KeyData("scroll",KeyData.BOOLEAN));
	    message.add(new KeyData("highlight",KeyData.BOOLEAN));
	    message.add(new KeyData("sound",KeyData.FILE));
	    message.add(new KeyData("variable",KeyData.KEY));
	    
	    message.add(new KeyData("Multiplayer", KeyData.SEPARATOR));
	    message.add(new KeyData("wait_description",KeyData.STRING,true));
	    result.put("[message]", message.toArray(keyData));
	    
	    LinkedList<KeyData> scenario = new LinkedList<KeyData>();
	    scenario.add(new KeyData("id",KeyData.KEY));
	    scenario.add(new KeyData("name",KeyData.STRING,true));
	    scenario.add(new KeyData("turns",KeyData.INTEGER));
	    scenario.add(new KeyData("map_data",KeyData.FILE));
	    scenario.add(new KeyData("next_scenario",KeyData.KEY));
	    scenario.add(new KeyData("victory_when_enemies_defeaded",KeyData.BOOLEAN));
	    result.put("[scenario]", scenario.toArray(keyData));
	    
	    LinkedList<KeyData> event = new LinkedList<KeyData>();
	    event.add(new KeyData("name",KeyData.KEY));
	    event.add(new KeyData("first_time_only",KeyData.BOOLEAN));
	    event.add(new KeyData("id",KeyData.KEY));
	    event.add(new KeyData("remove",KeyData.BOOLEAN));
	    result.put("[event]", event.toArray(keyData));
	    
	    
	    LinkedList<KeyData> option = new LinkedList<KeyData>();
	    option.add(new KeyData("message",KeyData.STRING,true));
	    result.put("[option]", option.toArray(keyData));
	    
	    
	    LinkedList<KeyData> village = new LinkedList<KeyData>();
	    side.add(new KeyData("x", KeyData.INTEGER));
	    side.add(new KeyData("y", KeyData.INTEGER));
	    result.put("[village]", village.toArray(keyData));
	    
	    
	    LinkedList<KeyData> part = new LinkedList<KeyData>();
	    part.add(new KeyData("background", KeyData.FILE));
	    part.add(new KeyData("story", KeyData.STRING,true));
	    part.add(new KeyData("show_title", KeyData.BOOLEAN));
	    result.put("[part]", part.toArray(keyData));
	    return result;
	}
}