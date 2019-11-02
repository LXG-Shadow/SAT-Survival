package com.lxgshadow.survival;

public class Messages {
    public static String tpa_Player404 = "找不到该玩家或该玩家已下线";
    public static String tpa_PlayerSelf = "你8能tp自己";
    public static String tpa_SendingRequest = "成功给 %(p) 发送了一个传送请求";
    public static String tpa_AcceptHint = "%(p) 希望传送到你的位置。输入 /tpa accept %(p) 来同意传送";
    public static String tpa_DenyHint =   "打xxx来拒绝传送";
    public static String tpa_NoRequest = "没有 %(p) 发送的传送请求或对方取消了传送";
    public static String tpa_CancelRequest =  "你成功取消了传送";
    public static String tpa_InCoolDown =  "你还需等待%(s)秒才能再次传送";

    public static String suicide_Msg = "AWSL";
    public static String broadcase_Msg = "[System Message]: %(m)";

    public static String team_404 ="没有找到该队伍";
    public static String team_NotIn = "你不在任何一个队伍里";
    public static String team_AlreadyIn = "你已经在队伍%(t)里了";
    public static String team_Player404 =  "找不到该玩家或该玩家已下线";
    public static String team_InviteHint = "%(p)邀请你加入队伍%(t), 输入/team join %(t) 来加入队伍";
    public static String team_InvitePlayerAlreadyIn =  "该玩家已经在一个队伍里了";
    public static String team_InviteNoPermission = "你没有邀请人的权限";
    public static String team_InviteRepeatRequest = "你已经邀请了该玩家了";
    public static String team_CreateSuccess = "你成功创建了队伍%(t)";
    public static String team_CreateSameName = "队伍名重复了，换一个名字把.";
    public static String team_JoinSuccess = "成功加入队伍%(t)";
    public static String team_JoinNoSpace = "队伍%(t)已经人满了";
    public static String team_JoinRequest = "成功向队伍%(t)发出了入队申请";
    public static String team_JoinRepeatRequest = "你已经向队伍%(t)发过了入队申请";
    public static String team_QuitSuccess = "你已经成功退出了队伍";
    public static String team_KickSuccess = "你成功踢出了队员%(p)";
    public static String team_KickVictimHint = "你被踢出了队伍%(t)";
    public static String team_KickPlayer404 = "该玩家不在你的队伍里面";
    public static String team_KickNoPermission = "你没有踢人的权限";
    public static String team_DisbandSuccess = "你成功解散了队伍";
    public static String team_DisbandNoPermission = "你没有解散的权限";
    public static String team_BroadcastJoin = "%(p)加入了队伍";
    public static String team_BroadcastQuit = "%(p)退出了队伍";
    public static String team_BroadcastKick = "%(p)被踢出了队伍";
    public static String team_BroadcastDisband = "队长解散了队伍";
    public static String team_BroadcastInvite = "%(p1)邀请%(p2)加入队伍";
    public static String team_AcceptHint ="%(p)申请加入队伍，输入/team accept %(p)来同意";
    public static String team_AcceptNoRequest = "该玩家没有向你发过入队申请或申请已过期";
    public static String team_AcceptNoPermission = "没有同意的权限";
    public static String team_AcceptNoSpace ="队伍已经满人了";
    public static String team_AcceptPlayerAlreadyIn =   "该玩家已经在一个队伍里了";
    public static String team_Info1 =  "-------------------------------------------------------";
    public static String team_Info2 =  "队伍名: %(t) - 人数: %(n)/%(mn)";
    public static String team_Info3 =   "队长: ";
    public static String team_Info4 =   "队友: ";
    public static String team_Info5 =   "    · %(p) - 血量: %(h)/20 位置: %(x),%(y),%(z) %(w)";

    public static String home_SetSuccess ="你成功设置了家-%(h)";
    public static String home_ReachLimit ="家的数量超出上限了";
    public static String home_SameNameExist ="有相同的名字了,换一个名字";
    public static String home_DeleteSuccess ="你成功删除了家-%(h)";
    public static String home_404 ="你没有叫%(h)的家";
    public static String home_TeleportSuccess = "你已经成功传送到家-%(h)";
    public static String home_List1 = "-------------------------------------------";
    public static String home_List2 = "你一共设置了%(n1)个家，你最多还能设置%(n2)个家";
    public static String home_List3 = "· %(h)";

    public static String vanish_msg1 = "你已经成功隐身了";
    public static String vanish_msg2 = "你取消了隐身";
}
