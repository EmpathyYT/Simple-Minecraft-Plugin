package derp1.tut;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;


public class derp extends JavaPlugin implements Listener {

    public List<String> list = new ArrayList<String>();

    public Inventory inv;



    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("doctor").setExecutor(new Heal());
        getLogger().info("on");
        CreateInv();

    }

    @Override
    public void onDisable() {
        getLogger().info("off");
    }

    //godboots command
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("changeteam")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;
            player.openInventory(inv);
            return true;
        }
        
        
        if (label.equalsIgnoreCase("quacktrident")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("quacktrident.use")) {
                return true;
            }
            if (player.getInventory().firstEmpty() == -1) {
                Location loc = player.getLocation();
                World world = player.getWorld();

                world.dropItemNaturally(loc, TridentThing());
                player.sendMessage("look under under your feet good sir");
                return true;
            }
            player.getInventory().addItem(TridentThing());
            player.sendMessage(ChatColor.DARK_RED + "Quack");
            return true;
        }
        if (label.equalsIgnoreCase("quacksword")) {
            if (!(sender instanceof Player)) {
                return true;
            }
        Player player = (Player) sender;
        if (!player.hasPermission("quacksword.use")) {
            return true;
        }
        if (player.getInventory().firstEmpty() == -1) {
            Location loc = player.getLocation();
            World world = player.getWorld();

            world.dropItemNaturally(loc, GodSword());
            player.sendMessage("look under under your feet good sir");
            return true;
        }
        player.getInventory().addItem(GodSword());
        player.sendMessage(ChatColor.DARK_RED + "Quack");
        return true;
        }

        if (label.equalsIgnoreCase("godboots")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("godboots.use")) {
                player.sendMessage("no.");
                return true;
            }
            if (player.getInventory().firstEmpty() == -1) {
                Location loc = player.getLocation();
                World world = player.getWorld();

                world.dropItemNaturally(loc, getItem());
                player.sendMessage("look under under your feet good sir");
                return true;
            }
            player.getInventory().addItem(getItem());
            player.sendMessage(ChatColor.GOLD + "quack");
            return true;
        }
        //godboots command



        //launch command
        if (label.equalsIgnoreCase("launch") || label.equalsIgnoreCase("lnch")) {
            if (!(sender instanceof Player)) {
                return true;


            }
            Player player = (Player) sender;
            if (player.hasPermission("launch.use")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.GREEN + "Zooommmm");
                    player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
                    return true;
                }
                if (isNum(args[0])) {
                    player.sendMessage(ChatColor.GREEN + "Zooommmm");
                    player.setVelocity(player.getLocation().getDirection().multiply(Integer.parseInt(args[0])).setY(2));
                }
                else {
                    player.sendMessage(ChatColor.RED + "Usage is /launch <number>");
                }
                return true;
            }
            player.sendMessage("no.");
            return true;


        }
        //launch command


        //hellooo
        if
   (label.equalsIgnoreCase("hello")) {
        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (player.hasPermission("hello.use")) {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Hey welcome to the server");
                return true;
            }
            player.sendMessage(ChatColor.RED + "no.");
            return true;


        }
        else {
            //console
            sender.sendMessage("Hey console");
            return true;
        }

        }
        return false;
    }
    //hellooo


    //launch command related junk
    public boolean isNum(String num) {
        try {
            Integer.parseInt(num);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //god boots related stoof, luanches u in the airrr
    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        Player player = (Player) event.getPlayer();
        if (player.getInventory().getBoots() != null)
            if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("God Boots"))
                if (player.getInventory().getBoots().getItemMeta().hasLore())
                    if (event.getFrom().getY() < event.getTo().getY() &&
                            player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
                        player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
                    }
    }
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(inv))
            return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;
        if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        if (event.getSlot() == 0 && event.getCurrentItem().getType() == Material.BLUE_WOOL) {
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changecolor(armor, Color.BLUE);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "Qauck");
        }
        if (event.getSlot() == 1 && event.getCurrentItem().getType() == Material.RED_WOOL) {
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changecolor(armor, Color.RED);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "Qauck");
        }
        if (event.getSlot() == 2 && event.getCurrentItem().getType() == Material.GREEN_WOOL) {
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changecolor(armor, Color.GREEN);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "Qauck");
        }
        if (event.getSlot() == 3 && event.getCurrentItem().getType() == Material.CYAN_WOOL) {
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changecolor(armor, Color.AQUA);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "Qauck");
        }
        if (event.getSlot() == 4 && event.getCurrentItem().getType() == Material.ORANGE_WOOL) {
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changecolor(armor, Color.ORANGE);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "Qauck");
        }
        if (event.getSlot() == 5 && event.getCurrentItem().getType() == Material.PURPLE_WOOL) {
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changecolor(armor, Color.PURPLE);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "Qauck");
        }
        if (event.getSlot() == 6 && event.getCurrentItem().getType() == Material.LIME_WOOL) {
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changecolor(armor, Color.LIME);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "Qauck");
        }
        if (event.getSlot() == 8 && event.getCurrentItem().getType() == Material.BARRIER) {
            player.closeInventory();
        }

        return;

    }














    //god boots iu4oiewutyiwefodafiguerwiope, protects u from fall damage
    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (player.getInventory().getBoots() != null)
                    if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("God Boots"))
                        if (player.getInventory().getBoots().getItemMeta().hasLore()) {
                            event.setCancelled(true);
                        }
            }
        }
    }
    @EventHandler
    public void OnTridentThing(PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TRIDENT))
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                Player player = (Player) event.getPlayer();
                if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!list.contains(player.getName()))
                        list.add(player.getName());
                    return;
                }

                if (event.getAction() == Action.LEFT_CLICK_AIR) {
                    player.launchProjectile(Fireball.class);

                }
            }
        if (list.contains(event.getPlayer().getName())) {
            list.remove(event.getPlayer().getName());
        }
    }

    @EventHandler
    public void onLand(ProjectileHitEvent event) {
        if (event.getEntityType() == EntityType.TRIDENT) {
            if (event.getEntity().getShooter() instanceof Player) {
                Player player = (Player) event.getEntity().getShooter();
                if (list.contains(player.getName())) {
                    Location loc = event.getEntity().getLocation();
                    loc.setY(loc.getY() + 1);

                    for (int i = 1; i < 4; i++) {
                        World world = (World) player.getWorld();
                        Zombie drowned = (Zombie) world.spawnEntity(loc, EntityType.ZOMBIE);
                        drowned.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                        drowned.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                        drowned.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                        drowned.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                        drowned.getEquipment().setItemInMainHand(GodSword());
                        drowned.setCustomName("Bob");
                        loc.setX(loc.getX() + i);

                    }
                }
            }
        }
    }




    @EventHandler
    public void OnGodSword(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (player.getInventory().getItemInMainHand() != null)
                if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Quack sword"))
                    if (player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                        player.setHealth(20.0);
                    }

        }
    }




    //diamond boots code stoof
    public ItemStack getItem() {
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta meta = boots.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "God Boots");
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "Boots by the God quack.");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);
        boots.setItemMeta(meta);
        return boots;
    }


    public ItemStack[] changecolor(ItemStack[] a, Color color) {
        for (ItemStack item : a) {
            try {
                if (item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE ||
                        item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.LEATHER_HELMET) {
                    LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                    meta.setColor(color);
                    item.setItemMeta(meta);
                }
            } catch (Exception e) {
                //nothing
            }
        }

        return a;
    }



    public ItemStack TridentThing() {
        ItemStack item = new ItemStack(Material.TRIDENT);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "quacktrident");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7(Right click) &a&oSpawn minions"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7(Left click) &a&oShoot explosives"));
        meta.setLore(lore);


        meta.addEnchant(Enchantment.LOYALTY, 10, true);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }


    public void CreateInv() {
        inv = Bukkit.createInventory(null, 9, ChatColor.DARK_RED + "Quack");

        ItemStack item = new ItemStack(Material.BLUE_WOOL);
        ItemMeta meta = item.getItemMeta();


        //blue
        meta.setDisplayName(ChatColor.BLUE + "Blue Team");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Click to join duck");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(0, item);


        //red
        item.setType(Material.RED_WOOL);
        meta.setDisplayName(ChatColor.RED + "Red Team");
        item.setItemMeta(meta);
        inv.setItem(1, item);


        item.setType(Material.GREEN_WOOL);
        meta.setDisplayName(ChatColor.DARK_GREEN + "Green Team");
        item.setItemMeta(meta);
        inv.setItem(2, item);



        item.setType(Material.CYAN_WOOL);
        meta.setDisplayName(ChatColor.BLUE + "Cyan Team");
        item.setItemMeta(meta);
        inv.setItem(3, item);


        item.setType(Material.ORANGE_WOOL);
        meta.setDisplayName(ChatColor.GOLD + "Orange Team");
        item.setItemMeta(meta);
        inv.setItem(4, item);


        item.setType(Material.PURPLE_WOOL);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Purple Team");
        item.setItemMeta(meta);
        inv.setItem(5, item);

        item.setType(Material.LIME_WOOL);
        meta.setDisplayName(ChatColor.GREEN + "Lime Team");
        item.setItemMeta(meta);
        inv.setItem(6, item);


        item.setType(Material.BARRIER);
        meta.setDisplayName( ChatColor.BOLD + "Close");
        lore.clear();
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(8, item);


    }













    public ItemStack GodSword() {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Quack sword");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.DARK_BLUE + "Power of quack");
        meta.setLore(lore);


        meta.addEnchant(Enchantment.DAMAGE_ALL, 32767, true);
        meta.addEnchant(Enchantment.SWEEPING_EDGE, 32767, true);

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);
        sword.setItemMeta(meta);
        return sword;
    }



}








