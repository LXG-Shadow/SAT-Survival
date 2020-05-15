package com.lxgshadow.easyduel;

public class Messages {
    public static String arena_splitor = "-------------------------------------";

    public static String arena_error_PlayerExist = "玩家 %p 已经在对局中了";
    public static String arena_error_PlayerOutOfBoarder = "玩家超出竞技区域，可能是场地大小";
    public static String arena_error_PlayerInDifferentTeam = "有同一玩家在不同的队伍中";

    public static String arena_error_Mode404 = "该模式不存在";

    public static String arena_error_Player404 = "玩家 %p 不在线";
    public static String arena_error_DuelSelf = "你不能Duel自己";

    public static String arena_error_NoRequest = "没有 %p 发送的对决请求";
    public static String arena_SendingRequest = "成功给 %p 发送了一个对决请求";
    public static String arena_AcceptHint = "%p 向你发起了对决(%m)请求。输入 /duel accept %p 来开始对决";
}
