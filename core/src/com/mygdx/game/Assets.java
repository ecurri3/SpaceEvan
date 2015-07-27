package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Assets {

	public static BitmapFont font12, font16, font20, font24, font36, font48,
			font72, font96;
	public static FreeTypeFontParameter parameter;
	public static FreeTypeFontGenerator gen;

	//
	// BACKGROUNDS
	//

	// tilt-control
	public static Texture texture_back;
	public static Sprite sprite_back;

	// button-control
	public static Texture texture_control_button_back;
	public static Sprite sprite_control_button_back;

	public static Texture texture_animback;
	public static Sprite sprite_animback;

	public static Texture texture_animback2;
	public static Sprite sprite_animback2;

	public static Texture texture_animback3;
	public static Sprite sprite_animback3;

	// menu-screen
	public static Texture texture_menuback;
	public static Sprite sprite_menuback;

	// option-screen
	public static Texture texture_option_screen_back;
	public static Sprite sprite_option_screen_back;

	// store-screen
	public static Texture texture_storeback;
	public static Sprite sprite_storeback;

	// store-template-two-column
	public static Texture texture_store_template_two;
	public static Sprite sprite_store_template_two;

	// store-template-three-column
	public static Texture texture_store_template_three;
	public static Sprite sprite_store_template_three;

	// missile_store_screen
	public static Texture texture_missile_screen_back;
	public static Sprite sprite_missile_screen_back;

	// power-store-screen
	public static Texture texture_power_screen_back;
	public static Sprite sprite_power_screen_back;

	// power-store-rapid-screen
	public static Texture texture_power_rapid_screen_back;
	public static Sprite sprite_power_rapid_screen_back;

	// confirm-purchase
	public static Texture texture_confirm_purchase;
	public static Sprite sprite_confirm_purchase;

	// cant-afford
	public static Texture texture_cant_afford;
	public static Sprite sprite_cant_afford;

	// do-not-own-prereqs
	public static Texture texture_prerequisites;
	public static Sprite sprite_prerequisites;

	// already-owned
	public static Texture texture_already_owned;
	public static Sprite sprite_already_owned;

	// purchased
	public static Texture texture_purchased;
	public static Sprite sprite_purchased;

	public static Texture texture_auto_aim;
	public static Sprite sprite_auto_aim;

	//
	// OPTION SCREEN INDICATORS
	//
	public static Texture texture_option_on, texture_option_off;
	public static Sprite sprite_option_on, sprite_option_off;

	public static Texture texture_option_tilt, texture_option_buttons;
	public static Sprite sprite_option_tilt, sprite_option_buttons;

	//
	// PLAYER SPRITE TURN ANIMATION
	//
	public static Sprite sprite_0normal;

	public static Sprite sprite_9left;
	public static Sprite sprite_18left;
	public static Sprite sprite_27left;
	public static Sprite sprite_36left;
	public static Sprite sprite_45left;

	public static Sprite sprite_9right;
	public static Sprite sprite_18right;
	public static Sprite sprite_27right;
	public static Sprite sprite_36right;
	public static Sprite sprite_45right;

	//
	// HEALTH BAR
	//
	public static Texture health_sheet;

	public static Sprite health_5;
	public static Sprite health_4;
	public static Sprite health_3;
	public static Sprite health_2;
	public static Sprite health_1;

	public static Texture extrahealth_sheet;

	public static Sprite extrahealth_5;
	public static Sprite extrahealth_4;
	public static Sprite extrahealth_3;
	public static Sprite extrahealth_2;
	public static Sprite extrahealth_1;

	//
	// POWERUP DISPLAY
	//
	public static Texture power_sheet;

	public static Sprite power_rapid;
	public static Sprite power_shotgun;
	public static Sprite power_multishot;
	public static Sprite power_auto;
	public static Sprite power_swiftness;
	public static Sprite power_big;

	public static Texture texture_sheet;
	public static Texture sheet;
	public static TextureRegion[] sheet_frames;
	public static TextureRegion current_frame;
	public static Animation explosion_animation;
	public static Animation big_explosion_animation;
	public static Animation static_animation;
	public static Animation back_animation;
	public static Animation flame_animation;

	public static Texture player_sheet;
	public static TextureRegion[] player_frames;
	public static TextureRegion current_player;

	//
	// SOUNDS
	//
	public static Sound select1;
	public static Sound select2;
	public static Sound fire1;
	public static Sound fire2;
	public static Sound fire3;
	public static Sound fire4;
	public static Sound fire5;
	public static Sound fire6;
	public static Sound bullet4;
	public static Sound bullet5;
	public static Sound bullet6;
	public static Sound bullet7;
	public static Sound bullet8;
	public static Sound bullet9;
	public static Sound explosion1;
	public static Sound explosion2;
	public static Sound explosion3;
	public static Sound explosion4;
	public static Sound explosion5;
	public static Sound explosion6;
	public static Sound powerup1;
	public static Sound powerup2;
	public static Sound powerup3;
	public static Sound powerup4;
	public static Sound powerup5;
	public static Sound powerup6;
	public static Sound powerup7;
	public static Sound heal1;

	public static Texture texture_player;
	public static Sprite player;

	public static Texture texture_missile_twin;
	public static Sprite missile_twin;

	public static Texture texture_bullet_twin;
	public static Sprite bullet_twin;

	//
	// MISSILE
	//
	public static Texture texture_missile;
	public static Sprite missile;

	public static Texture texture_bigmissile;
	public static Sprite bigmissile;

	//
	// BULLET
	//
	public static Texture texture_bullet;
	public static Sprite bullet;

	public static Texture texture_bigbullet;
	public static Sprite bigbullet;

	//
	// ENEMY
	//
	public static Texture texture_enemy;
	public static Sprite enemy;

	// gold-enemy
	public static Texture texture_gold_enemy;
	public static Sprite gold_enemy;

	// strong-enemy
	public static Texture texture_strong_enemy;
	public static Sprite strong_enemy;

	// landmine
	public static Texture texture_landmine;
	public static Sprite landmine;

	public static Texture texture_cannon;
	public static Sprite cannon;

	//
	// POWERUP ICONS
	//
	public static Texture texture_rapid;
	public static Sprite powerup_rapid;

	public static Texture texture_spray;
	public static Sprite powerup_spray;

	public static Texture texture_multi;
	public static Sprite powerup_multi;

	public static Texture texture_life;
	public static Sprite powerup_life;

	public static Texture texture_auto;
	public static Sprite powerup_auto;

	public static Texture texture_swiftness;
	public static Sprite powerup_swiftness;

	public static Texture texture_big;
	public static Sprite powerup_big;

	//
	// PAUSE
	//
	public static Texture texture_pause;
	public static Sprite pause;

	//
	// PLAYBUTTON
	//
	public static Texture texture_playbutton;
	public static Sprite playbutton;

	//
	//
	// GAMEOVER
	public static Texture texture_gameover_screen;
	public static Sprite gameover_screen;

	//
	// PLAYAGAIN
	//
	public static Texture texture_playagain;
	public static Sprite playagain;

	public static void load() {

		// FONT SIZES
		gen = new FreeTypeFontGenerator(Gdx.files.internal("OCRASTD.OTF"));
		parameter = new FreeTypeFontParameter();

		parameter.size = 12;
		font12 = new BitmapFont();
		font12 = gen.generateFont(parameter);
		font12.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font12.setColor(Color.valueOf("ffff00"));
		font12.setScale(1, -1);

		parameter.size = 16;
		font16 = gen.generateFont(parameter);
		font16.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font16.setColor(Color.valueOf("ffff00"));
		font16.setScale(1, -1);

		parameter.size = 20;
		font20 = gen.generateFont(parameter);
		font20.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font20.setColor(Color.valueOf("ffff00"));
		font20.setScale(1, -1);

		parameter.size = 24;
		font24 = gen.generateFont(parameter);
		font24.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font24.setColor(Color.valueOf("ffff00"));
		font24.setScale(1, -1);

		parameter.size = 36;
		font36 = gen.generateFont(parameter);
		font36.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font36.setColor(Color.valueOf("ffff00"));
		font36.setScale(1, -1);

		parameter.size = 48;
		font48 = gen.generateFont(parameter);
		font48.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font48.setColor(Color.valueOf("ffff00"));
		font48.setScale(1, -1);

		parameter.size = 72;
		font72 = gen.generateFont(parameter);
		font72.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font72.setColor(Color.valueOf("ffff00"));
		font72.setScale(1, -1);

		parameter.size = 96;
		font96 = gen.generateFont(parameter);
		font96.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font96.setColor(Color.valueOf("ffff00"));
		font96.setScale(1, -1);

		gen.dispose();

		texture_player = new Texture(
				Gdx.files.internal("images/player/player2.0_0normal.png"));
		player = new Sprite(texture_player);
		player.flip(false, true);

		texture_missile_twin = new Texture(
				Gdx.files.internal("images/player/missile_twin.png"));
		missile_twin = new Sprite(texture_missile_twin);
		missile_twin.flip(false, true);

		texture_bullet_twin = new Texture(
				Gdx.files.internal("images/player/bullet_twin.png"));
		bullet_twin = new Sprite(texture_bullet_twin);
		bullet_twin.flip(false, true);

		texture_missile = new Texture(
				Gdx.files.internal("images/missile/missile.png"));
		missile = new Sprite(texture_missile);

		texture_bigmissile = new Texture(
				Gdx.files.internal("images/missile/bigmissile.png"));
		bigmissile = new Sprite(texture_bigmissile);

		texture_bullet = new Texture(
				Gdx.files.internal("images/bullet/bullet.png"));
		bullet = new Sprite(texture_bullet);

		texture_bigbullet = new Texture(
				Gdx.files.internal("images/bullet/bigbullet.png"));
		bigbullet = new Sprite(texture_bigbullet);

		//
		// ENEMY
		//
		texture_enemy = new Texture(
				Gdx.files.internal("images/enemies/enemy_circle.png"));
		enemy = new Sprite(texture_enemy);
		enemy.flip(false, true);

		texture_gold_enemy = new Texture(
				Gdx.files.internal("images/enemies/gold_enemy.png"));
		gold_enemy = new Sprite(texture_gold_enemy);
		gold_enemy.flip(false, true);

		texture_strong_enemy = new Texture(
				Gdx.files.internal("images/enemies/strong_enemy.png"));
		strong_enemy = new Sprite(texture_strong_enemy);
		strong_enemy.flip(false, true);

		// landmine
		texture_landmine = new Texture(
				Gdx.files.internal("images/bullet/landmine.png"));
		landmine = new Sprite(texture_landmine);
		landmine.flip(false, true);

		// cannon
		texture_cannon = new Texture(
				Gdx.files.internal("images/player/cannon.png"));
		cannon = new Sprite(texture_cannon);
		cannon.flip(false, true);

		texture_rapid = new Texture(
				Gdx.files.internal("images/powerups/powerup_rapid_circle.png"));
		powerup_rapid = new Sprite(texture_rapid);
		powerup_rapid.flip(false, true);

		texture_spray = new Texture(
				Gdx.files
						.internal("images/powerups/powerup_shotgun_circle.png"));
		powerup_spray = new Sprite(texture_spray);
		powerup_spray.flip(false, true);

		texture_multi = new Texture(
				Gdx.files.internal("images/powerups/powerup_multi_circle.png"));
		powerup_multi = new Sprite(texture_multi);
		powerup_multi.flip(false, true);

		texture_life = new Texture(
				Gdx.files.internal("images/powerups/powerup_life_circle.png"));
		powerup_life = new Sprite(texture_life);
		powerup_life.flip(false, true);

		texture_auto = new Texture(
				Gdx.files.internal("images/powerups/powerup_auto_circle.png"));
		powerup_auto = new Sprite(texture_auto);
		powerup_auto.flip(false, true);

		texture_swiftness = new Texture(
				Gdx.files
						.internal("images/powerups/powerup_swiftness_circle.png"));
		powerup_swiftness = new Sprite(texture_swiftness);
		powerup_swiftness.flip(false, true);

		texture_big = new Texture(
				Gdx.files.internal("images/powerups/powerup_big_circle.png"));
		powerup_big = new Sprite(texture_big);
		powerup_big.flip(false, true);

		texture_gameover_screen = new Texture(
				Gdx.files.internal("images/interface/gameover_screen.png"));
		gameover_screen = new Sprite(texture_gameover_screen);
		gameover_screen.flip(false, true);

		texture_pause = new Texture(
				Gdx.files.internal("images/interface/pause_screen.png"));
		texture_pause.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		pause = new Sprite(texture_pause);
		pause.flip(false, true);

		// Sound
		select1 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/buttons/select1.wav"));
		select2 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/buttons/select2.wav"));
		fire1 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/missile/fire1.wav"));
		fire2 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/missile/fire2.wav"));
		fire3 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/missile/fire3.wav"));
		fire4 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/missile/fire4.wav"));
		fire5 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/missile/fire5.wav"));
		fire6 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/missile/fire6.wav"));
		explosion1 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/explosion/explosion1.wav"));
		explosion2 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/explosion/explosion2.wav"));
		explosion3 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/explosion/explosion3.wav"));
		explosion4 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/explosion/explosion4.wav"));
		explosion5 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/explosion/explosion5.wav"));
		explosion6 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/explosion/explosion6.wav"));
		powerup1 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/powerups/powerup1.wav"));
		powerup2 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/powerups/powerup2.wav"));
		powerup3 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/powerups/powerup3.wav"));
		powerup4 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/powerups/powerup4.wav"));
		powerup5 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/powerups/powerup5.wav"));
		powerup6 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/powerups/powerup6.wav"));
		powerup7 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/powerups/powerup7.wav"));
		heal1 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/powerups/heal1.wav"));
		bullet4 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/bullet/bullet4.wav"));
		bullet5 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/bullet/bullet5.wav"));
		bullet6 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/bullet/bullet6.wav"));
		bullet7 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/bullet/bullet7.wav"));
		bullet8 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/bullet/bullet8.wav"));
		bullet9 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/bullet/bullet9.wav"));

		//
		// Background
		//
		texture_back = new Texture(
				Gdx.files.internal("images/interface/background_android.png"));
		texture_back.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite_back = new Sprite(texture_back);
		sprite_back.flip(false, true);

		texture_control_button_back = new Texture(
				Gdx.files
						.internal("images/interface/background_android_control_button.png"));
		texture_control_button_back.setFilter(TextureFilter.Linear,
				TextureFilter.Linear);
		sprite_control_button_back = new Sprite(texture_control_button_back);
		sprite_control_button_back.flip(false, true);

		texture_animback = new Texture(
				Gdx.files.internal("images/interface/scrollingBack_1.png"));
		texture_animback.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite_animback = new Sprite(texture_animback);
		sprite_animback.flip(false, true);

		texture_animback2 = new Texture(
				Gdx.files.internal("images/interface/scrollingBack_2.png"));
		texture_animback2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite_animback2 = new Sprite(texture_animback2);
		sprite_animback2.flip(false, true);

		texture_animback3 = new Texture(
				Gdx.files.internal("images/interface/scrollingBack_3.png"));
		texture_animback3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite_animback3 = new Sprite(texture_animback3);
		sprite_animback3.flip(false, true);

		texture_auto_aim = new Texture(
				Gdx.files.internal("images/player/auto_aim.png"));
		texture_auto_aim.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite_auto_aim = new Sprite(texture_auto_aim);

		// Loading animation
		sheet = new Texture(
				Gdx.files.internal("animations/explosions/explosion.png"));
		TextureRegion[][] temp = TextureRegion.split(sheet, 100, 100);
		sheet_frames = new TextureRegion[20];

		int index = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 20; j++) {
				sheet_frames[index++] = temp[i][j];
			}
		}

		for (int i = 0; i < 20; i++) {
			sheet_frames[i].flip(false, true);
		}

		explosion_animation = new Animation(0.05F, sheet_frames);

		// Loading animation
		sheet = new Texture(
				Gdx.files.internal("animations/explosions/bigEXP_squished.png"));
		TextureRegion[][] temp2 = TextureRegion.split(sheet, 600, 600);
		sheet_frames = new TextureRegion[5];

		index = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 5; j++) {
				sheet_frames[index++] = temp2[i][j];
			}
		}

		for (int i = 0; i < 5; i++) {
			sheet_frames[i].flip(false, true);
		}

		big_explosion_animation = new Animation(0.15F, sheet_frames);

		// Loading animation
		sheet = new Texture(
				Gdx.files.internal("animations/powerups/shield_sheet.png"));
		TextureRegion[][] temp3 = TextureRegion.split(sheet, 200, 200);
		sheet_frames = new TextureRegion[10];

		index = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 10; j++) {
				sheet_frames[index++] = temp3[i][j];
			}
		}

		for (int i = 0; i < 10; i++) {
			sheet_frames[i].flip(false, true);
		}

		static_animation = new Animation(0.05F, sheet_frames);

		// Loading animation
		sheet = new Texture(Gdx.files.internal("animations/player/flame_animation.png"));
		TextureRegion[][] temp4 = TextureRegion.split(sheet, 160, 160);
		sheet_frames = new TextureRegion[3];

		index = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 3; j++) {
				sheet_frames[index++] = temp4[i][j];
			}
		}

		for (int i = 0; i < 3; i++) {
			sheet_frames[i].flip(false, true);
		}

		flame_animation = new Animation(0.05F, sheet_frames);

		health_sheet = new Texture(
				Gdx.files.internal("images/interface/health_sheet_small.png"));

		health_5 = new Sprite(health_sheet, 0, 0, 210, 75);
		health_4 = new Sprite(health_sheet, 0, 75, 210, 75);
		health_3 = new Sprite(health_sheet, 0, 150, 210, 75);
		health_2 = new Sprite(health_sheet, 0, 225, 210, 75);
		health_1 = new Sprite(health_sheet, 0, 300, 210, 75);

		extrahealth_sheet = new Texture(
				Gdx.files.internal("images/interface/extrahealth_sheet.png"));

		extrahealth_5 = new Sprite(extrahealth_sheet, 0, 0, 18, 90);
		extrahealth_4 = new Sprite(extrahealth_sheet, 18, 0, 18, 90);
		extrahealth_3 = new Sprite(extrahealth_sheet, 36, 0, 18, 90);
		extrahealth_2 = new Sprite(extrahealth_sheet, 54, 0, 18, 90);
		extrahealth_1 = new Sprite(extrahealth_sheet, 72, 0, 18, 90);

		power_sheet = new Texture(
				Gdx.files.internal("images/interface/powerup_text.png"));

		power_rapid = new Sprite(power_sheet, 0, 0, 160, 35);
		power_rapid.flip(false, true);
		power_shotgun = new Sprite(power_sheet, 0, 35, 160, 35);
		power_shotgun.flip(false, true);
		power_multishot = new Sprite(power_sheet, 0, 70, 160, 35);
		power_multishot.flip(false, true);
		power_auto = new Sprite(power_sheet, 160, 0, 160, 35);
		power_auto.flip(false, true);
		power_swiftness = new Sprite(power_sheet, 160, 35, 160, 35);
		power_swiftness.flip(false, true);
		power_big = new Sprite(power_sheet, 160, 70, 160, 35);
		power_big.flip(false, true);
	}

}
