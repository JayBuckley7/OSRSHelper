package com.infonuascape.osrshelper.models.players;

import com.infonuascape.osrshelper.enums.SkillType;
import com.infonuascape.osrshelper.models.Skill;

import java.util.ArrayList;
import java.util.List;

public class PlayerSkills {
	public Skill overall = new Skill(SkillType.Overall ,2);
	public Skill attack = new Skill(SkillType.Attack,4);
	public Skill defence = new Skill(SkillType.Defence,6);
	public Skill strength = new Skill(SkillType.Strength,8);
	public Skill hitpoints = new Skill(SkillType.Hitpoints,10);
	public Skill ranged = new Skill(SkillType.Ranged,12);
	public Skill prayer = new Skill(SkillType.Prayer,14);
	public Skill magic = new Skill(SkillType.Magic,16);
	public Skill cooking = new Skill(SkillType.Cooking,18);
	public Skill woodcutting = new Skill(SkillType.Woodcutting,20);
	public Skill fletching = new Skill(SkillType.Fletching,22);
	public Skill fishing = new Skill(SkillType.Fishing,24);
	public Skill firemaking = new Skill(SkillType.Firemaking,26);
	public Skill crafting = new Skill(SkillType.Crafting,28);
	public Skill smithing = new Skill(SkillType.Smithing,30);
	public Skill mining = new Skill(SkillType.Mining,32);
	public Skill herblore = new Skill(SkillType.Herblore,34);
	public Skill agility = new Skill(SkillType.Agility,36);
	public Skill thieving = new Skill(SkillType.Thieving,38);
	public Skill slayer = new Skill(SkillType.Slayer,40);
	public Skill farming = new Skill(SkillType.Farming,42);
	public Skill runecraft = new Skill(SkillType.Runecrafting,44);
	public Skill hunter = new Skill(SkillType.Hunter,46);
	public Skill construction = new Skill(SkillType.Construction,48);

	public String lastUpdate;

	public List<Skill> skillList = new ArrayList<Skill>();

	public PlayerSkills() {
		skillList.add(this.overall);
		skillList.add(this.attack);
		skillList.add(this.defence);
		skillList.add(this.strength);
		skillList.add(this.hitpoints);
		skillList.add(this.ranged);
		skillList.add(this.prayer);
		skillList.add(this.magic);
		skillList.add(this.cooking);
		skillList.add(this.woodcutting);
		skillList.add(this.fletching);
		skillList.add(this.fishing);
		skillList.add(this.firemaking);
		skillList.add(this.crafting);
		skillList.add(this.smithing);
		skillList.add(this.mining);
		skillList.add(this.herblore);
		skillList.add(this.agility);
		skillList.add(this.thieving);
		skillList.add(this.slayer);
		skillList.add(this.farming);
		skillList.add(this.runecraft);
		skillList.add(this.hunter);
		skillList.add(this.construction);
	}

	public static ArrayList<Skill> getSkillsInOrder(PlayerSkills playerSkills) {
		ArrayList<Skill> skills = new ArrayList<Skill>();

		skills.add(playerSkills.overall);
		skills.add(playerSkills.attack);
		skills.add(playerSkills.defence);
		skills.add(playerSkills.strength);
		skills.add(playerSkills.hitpoints);
		skills.add(playerSkills.ranged);
		skills.add(playerSkills.prayer);
		skills.add(playerSkills.magic);
		skills.add(playerSkills.cooking);
		skills.add(playerSkills.woodcutting);
		skills.add(playerSkills.fletching);
		skills.add(playerSkills.fishing);
		skills.add(playerSkills.firemaking);
		skills.add(playerSkills.crafting);
		skills.add(playerSkills.smithing);
		skills.add(playerSkills.mining);
		skills.add(playerSkills.herblore);
		skills.add(playerSkills.agility);
		skills.add(playerSkills.thieving);
		skills.add(playerSkills.slayer);
		skills.add(playerSkills.farming);
		skills.add(playerSkills.runecraft);
		skills.add(playerSkills.hunter);
		skills.add(playerSkills.construction);

		return skills;
	}

	public static ArrayList<Skill> getSkillsInOrderForRSView(PlayerSkills playerSkills) {
		ArrayList<Skill> skills = new ArrayList<Skill>();

		skills.add(playerSkills.attack);
		skills.add(playerSkills.hitpoints);
		skills.add(playerSkills.mining);
		skills.add(playerSkills.strength);
		skills.add(playerSkills.agility);
		skills.add(playerSkills.smithing);
		skills.add(playerSkills.defence);
		skills.add(playerSkills.herblore);
		skills.add(playerSkills.fishing);
		skills.add(playerSkills.ranged);
		skills.add(playerSkills.thieving);
		skills.add(playerSkills.cooking);
		skills.add(playerSkills.prayer);
		skills.add(playerSkills.crafting);
		skills.add(playerSkills.firemaking);
		skills.add(playerSkills.magic);
		skills.add(playerSkills.fletching);
		skills.add(playerSkills.woodcutting);
		skills.add(playerSkills.runecraft);
		skills.add(playerSkills.slayer);
		skills.add(playerSkills.farming);
		skills.add(playerSkills.construction);
		skills.add(playerSkills.hunter);
		skills.add(playerSkills.overall);

		return skills;
	}
}
