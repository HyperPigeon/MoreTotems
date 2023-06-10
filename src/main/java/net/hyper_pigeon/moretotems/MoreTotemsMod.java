package net.hyper_pigeon.moretotems;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.hyper_pigeon.moretotems.effects.Cephalopod;
import net.hyper_pigeon.moretotems.effects.Necrosis;
import net.hyper_pigeon.moretotems.effects.Sniper;
import net.hyper_pigeon.moretotems.entity.SummonedBeeEntity;
import net.hyper_pigeon.moretotems.entity.SummonedZombieEntity;
import net.hyper_pigeon.moretotems.item.*;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class MoreTotemsMod implements ModInitializer {

	/*an instance of our new item, the explosive totem of undying, with a max stack size of 1*/
	public static final ExplosiveTotemOfUndying EXPLOSIVE_TOTEM_OF_UNDYING  =
			new ExplosiveTotemOfUndying(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));

	public static final StingingTotemOfUndying STINGING_TOTEM_OF_UNDYING  =
			new StingingTotemOfUndying(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));

	public static final TeleportingTotemOfUndying TELEPORTING_TOTEM_OF_UNDYING =
			new TeleportingTotemOfUndying(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));

	public static final GhastlyTotemOfUndying GHASTLY_TOTEM_OF_UNDYING = new GhastlyTotemOfUndying
			(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));

	public static final SkeletalTotemOfUndying SKELETAL_TOTEM_OF_UNDYING =
			new SkeletalTotemOfUndying (new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));

	public static final TentacledTotemOfUndying TENTACLED_TOTEM_OF_UNDYING =
			new TentacledTotemOfUndying(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));


	public static final RottingTotemOfUndying ROTTING_TOTEM_OF_UNDYING =
			new RottingTotemOfUndying(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));

	public static final EntityType<SummonedBeeEntity> SUMMONED_BEE_ENTITY =
			Registry.register(
					Registries.ENTITY_TYPE,
					new Identifier("moretotems", "summoned_bee"),
					FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SummonedBeeEntity::new).dimensions(EntityDimensions.fixed(1, 2)).build()

			);


	public static final EntityType<SummonedZombieEntity> SUMMONED_ZOMBIE_ENTITY = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier("moretotems", "summoned_zombie"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SummonedZombieEntity::new).dimensions(EntityDimensions.fixed(1, 2)).build());



	public static final Sniper SNIPER = Registry.register(Registries.STATUS_EFFECT, new Identifier("moretotems", "sniper"), new Sniper(StatusEffectCategory.BENEFICIAL, 13420603));

	public static final Cephalopod CEPHALOPOD = Registry.register(Registries.STATUS_EFFECT, new Identifier("moretotems", "cephalopod"), new Cephalopod(StatusEffectCategory.BENEFICIAL, 23245245));

	public static final Necrosis NECROSIS = Registry.register(Registries.STATUS_EFFECT, new Identifier("moretotems", "necrosis"), new Necrosis(StatusEffectCategory.BENEFICIAL, 23245245));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		// an instance of our new item
		Registry.register(Registries.ITEM, new Identifier("moretotems", "explosive_totem_of_undying"), EXPLOSIVE_TOTEM_OF_UNDYING);
		Registry.register(Registries.ITEM, new Identifier("moretotems", "stinging_totem_of_undying"), STINGING_TOTEM_OF_UNDYING);
		Registry.register(Registries.ITEM, new Identifier("moretotems", "teleporting_totem_of_undying"), TELEPORTING_TOTEM_OF_UNDYING);
		Registry.register(Registries.ITEM, new Identifier("moretotems", "ghastly_totem_of_undying"), GHASTLY_TOTEM_OF_UNDYING);
		Registry.register(Registries.ITEM, new Identifier("moretotems", "skeletal_totem_of_undying"), SKELETAL_TOTEM_OF_UNDYING);
		Registry.register(Registries.ITEM, new Identifier("moretotems", "tentacled_totem_of_undying"), TENTACLED_TOTEM_OF_UNDYING);
		Registry.register(Registries.ITEM, new Identifier("moretotems", "rotting_totem_of_undying"), ROTTING_TOTEM_OF_UNDYING);
		FabricDefaultAttributeRegistry.register(MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY, ZombieEntity.createZombieAttributes());
		FabricDefaultAttributeRegistry.register(MoreTotemsMod.SUMMONED_BEE_ENTITY, SummonedBeeEntity.createTotemBeeAttributes());
	}
}
