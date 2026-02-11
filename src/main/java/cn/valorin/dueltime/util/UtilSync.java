package cn.valorin.dueltime.util;

import cn.valorin.dueltime.DuelTimePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.concurrent.CompletableFuture;

/**
 * 同步工具类 - 已更新为使用安全的传送方法
 */
public class UtilSync {
    public static void publishEvent(Event event) {
        // 在Folia环境中直接同步调用事件，避免调度器调用
        if (SchedulerUtil.isFolia()) {
            Bukkit.getServer().getPluginManager().callEvent(event);
        } else {
            SchedulerUtil.runTask(() -> Bukkit.getServer().getPluginManager().callEvent(event));
        }
    }

    /**
     * 安全的传送方法（已弃用，推荐使用SchedulerUtil.safeTeleport）
     */
    @Deprecated
    public static void tp(Player player, Location location) {
        SchedulerUtil.runTask(() -> {
            if (player.isOnline() && location.getWorld() != null) {
                player.teleport(location);
            }
        });
    }
    
    /**
     * 新的安全传送方法
     */
    public static CompletableFuture<Boolean> safeTeleport(Player player, Location location) {
        return SchedulerUtil.safeTeleport(player, location);
    }
}
