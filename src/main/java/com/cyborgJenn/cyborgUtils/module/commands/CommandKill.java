package com.cyborgJenn.cyborgUtils.module.commands;

import com.cyborgJenn.cyborgUtils.CyborgUtils;
import com.cyborgJenn.cyborgUtils.core.utils.Config;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandKill extends Command{

	public CommandKill() {
		super("kill");
		
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender var1) {
		return Config.requireOpKillSelf ? super.canCommandSenderUseCommand(var1) : true;
	}
	
	public boolean isUsernameIndex(int var1) {
		return var1 == 0;
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "/"+name+" [player]";
	}
	
	public static class ForcedDamageSource extends DamageSource {
		public static final ForcedDamageSource forced = new ForcedDamageSource("generic");
		
		public ForcedDamageSource(String name) {
			super(name);
		}
		
		public static ForcedEntityDamageSource causeEntityDamage(Entity entity) {
			return new ForcedEntityDamageSource("indirectMagic", entity);
		}
		
		static {
			forced.setDamageBypassesArmor();
			forced.setDamageAllowedInCreativeMode();
		}
	}
	
	public static class ForcedEntityDamageSource extends EntityDamageSource {
		public ForcedEntityDamageSource(String name, Entity entity) {
			super(name, entity);
			
			setDamageBypassesArmor();
			setDamageAllowedInCreativeMode();
		}		
	}
	
	public static class EntityDummy extends Entity {
		private final String username;
		
		public EntityDummy(String username) {
			super(CyborgUtils.server.worldServers[0]);
			
			this.username = username;
		}

		@Override
		public void entityInit() {}

		@Override
		public void readEntityFromNBT(NBTTagCompound var1) {}

		@Override
		public void writeEntityToNBT(NBTTagCompound var1) {}
		
		public String getEntityName() {
			return username;
		}
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = args.length < 1 ? getCommandSenderAsPlayer(sender) : CyborgUtils.server.getPlayerList().getPlayerByUsername(args[0]);
		if (player == null) throw new PlayerNotFoundException();
		
		if (sender != player || Config.requireOpKillSelf && !sender.canCommandSenderUseCommand(2, name)) { // hack!
			sender.addChatMessage(new TextComponentTranslation("commands.permission.false"));
			return;
		}
		
		if (sender == player) player.attackEntityFrom(ForcedDamageSource.forced, 32767);
		else player.attackEntityFrom(ForcedDamageSource.causeEntityDamage(sender instanceof Entity ? (Entity)sender : new EntityDummy(sender.getName())), 32767);
		
		notifyOperators(sender, this, "Killing "+player.getName());
		
		
	}

}
