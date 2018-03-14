package ww.rong;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;

import org.aspectj.weaver.patterns.IfPointcut.IfFalsePointcut;
import org.springframework.util.StringUtils;

import model.rongCloud.CheckOnlineResult;
import model.rongCloud.CodeSuccessResult;
import model.rongCloud.GroupInfo;
import model.rongCloud.GroupUserQueryResult;
import model.rongCloud.HistoryMessageResult;
import model.rongCloud.ListGagGroupUserResult;
import model.rongCloud.ListWordfilterResult;
import model.rongCloud.QueryBlacklistUserResult;
import model.rongCloud.QueryBlockUserResult;
import model.rongCloud.TemplateMessage;
import model.rongCloud.TokenResult;
import ww.rong.messages.BaseMessage;
import ww.rong.messages.TxtMessage;
import ww.rong.util.GsonUtil;

public class RongCloudUtil {

	private static final String JSONFILE = RongCloudUtil.class.getClassLoader().getResource("jsonsource").getPath()+"/";
	public static String appKey ="qd46yzrfqhp1f"; // "25wehl3u29gfw";//替换成您的appkey
	public static String appSecret = "FxGzUj4ioVv6"; //替换成匹配上面key的secret
	public static RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
	public static Reader reader = null ;
	public static String Message="";
	//获取融云token
	public static TokenResult getToken(String userId, String realname, String avatar){
		if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(realname)||StringUtils.isEmpty(avatar)) {
			return null;
		}
		TokenResult userGetTokenResult=null;
		try {
			userGetTokenResult = rongCloud.user.getToken(userId, realname, avatar);
		} catch (Exception e) {
			Message=e.getMessage();
			return null;
		}
		System.out.println("getToken:  " + userGetTokenResult.toString());
		return userGetTokenResult;
	}

	// 刷新用户信息方法 
	public static CodeSuccessResult refresh(String userId, String realname, String avatar) throws Exception{
		if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(realname)||StringUtils.isEmpty(avatar)) {
			return null;
		}
		CodeSuccessResult userRefreshResult = rongCloud.user.refresh(userId, realname, avatar);
		System.out.println("refresh:  " + userRefreshResult.toString());
		return userRefreshResult;
	}
			
	// 检查用户在线状态 方法 
	public static CheckOnlineResult checkOnline(String userId) throws Exception{
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		CheckOnlineResult userCheckOnlineResult = rongCloud.user.checkOnline(userId);
		System.out.println("checkOnline:  " + userCheckOnlineResult.toString());
		return userCheckOnlineResult;
	}
			
	// 封禁用户方法（每秒钟限 100 次）
	public static CodeSuccessResult block(String userId,int time) throws Exception{
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		CodeSuccessResult userBlockResult = rongCloud.user.block(userId, time);
		System.out.println("block:  " + userBlockResult.toString());
		return userBlockResult;
	}
			
	// 解除用户封禁方法（每秒钟限 100 次） 
	public static CodeSuccessResult unBlock(String userId) throws Exception{
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		CodeSuccessResult userUnBlockResult = rongCloud.user.unBlock(userId);
		System.out.println("unBlock:  " + userUnBlockResult.toString());
		return userUnBlockResult;
	}
			
	// 获取被封禁用户方法（每秒钟限 100 次） 
	public static QueryBlockUserResult queryBlock() throws Exception{
		QueryBlockUserResult userQueryBlockResult = rongCloud.user.queryBlock();
		System.out.println("queryBlock:  " + userQueryBlockResult.toString());
		return userQueryBlockResult;
	}
	
	// 添加用户到黑名单方法（每秒钟限 100 次） 
	public static CodeSuccessResult addBlacklist(String userId,String toUserId) throws Exception{
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		if (StringUtils.isEmpty(toUserId)) {
			return null;
		}
		CodeSuccessResult userAddBlacklistResult = rongCloud.user.addBlacklist(userId, toUserId);
		System.out.println("addBlacklist:  " + userAddBlacklistResult.toString());
		return userAddBlacklistResult;
	}
	
	// 获取某用户的黑名单列表方法（每秒钟限 100 次） 
	public static QueryBlacklistUserResult queryBlacklist(String userId) throws Exception{
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		QueryBlacklistUserResult userQueryBlacklistResult = rongCloud.user.queryBlacklist(userId);
		System.out.println("queryBlacklist:  " + userQueryBlacklistResult.toString());
		return userQueryBlacklistResult;
	}
			
	// 从黑名单中移除用户方法（每秒钟限 100 次） 
	public static CodeSuccessResult removeBlacklist(String userId,String toUserId) throws Exception{
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		if (StringUtils.isEmpty(toUserId)) {
			return null;
		}
		CodeSuccessResult userRemoveBlacklistResult = rongCloud.user.removeBlacklist(userId, toUserId);
		System.out.println("removeBlacklist:  " + userRemoveBlacklistResult.toString());
		return userRemoveBlacklistResult;
	}
			
	//************************Message********************
	// 发送单聊消息方法（一个用户向另外一个用户发送消息，单条消息最大 128k。每分钟最多发送 6000 条信息，每次发送用户上限为 1000 人，如：一次发送 1000 人时，示为 1000 条消息。） 
	public static 	CodeSuccessResult 	publishPrivate(String userId,String[] messagePublishPrivateToUserId,BaseMessage messagePublishPrivateVoiceMessage) {
		
		if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(messagePublishPrivateVoiceMessage)||messagePublishPrivateToUserId.length<=0) {
			return null;
		}
		CodeSuccessResult messagePublishPrivateResult = null;
		try {
			messagePublishPrivateResult = rongCloud.message.publishPrivate(userId, messagePublishPrivateToUserId, messagePublishPrivateVoiceMessage, "thisisapush", "{\"pushData\":\"hello\"}", "4", 0, 0, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("publishPrivate:  " + messagePublishPrivateResult.toString());
		return messagePublishPrivateResult;
	}
	
	// 发送单聊模板消息方法（一个用户向多个用户发送不同消息内容，单条消息最大 128k。每分钟最多发送 6000 条信息，每次发送用户上限为 1000 人。） 
	public static 	CodeSuccessResult	publishTemplate(){
		CodeSuccessResult messagePublishTemplateResult=null;
		try {
			reader = new InputStreamReader(new FileInputStream(JSONFILE+"TemplateMessage.json"));
			TemplateMessage publishTemplateTemplateMessage  =  (TemplateMessage)GsonUtil.fromJson(reader, TemplateMessage.class);
			messagePublishTemplateResult = rongCloud.message.publishTemplate(publishTemplateTemplateMessage);
			System.out.println("publishTemplate:  " + messagePublishTemplateResult.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
				try {
					if(null != reader){
						reader.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return messagePublishTemplateResult; 
	}
	
	// 发送系统消息方法（一个用户向一个或多个用户发送系统消息，单条消息最大 128k，会话类型为 SYSTEM。每秒钟最多发送 100 条消息，每次最多同时向 100 人发送，如：一次发送 100 人时，示为 100 条消息。） 
	public static CodeSuccessResult PublishSystem(String userId,String[] messagePublishSystemToUserId,TxtMessage messagePublishSystemTxtMessage) throws Exception{
		if (messagePublishSystemToUserId.length>0||StringUtils.isEmpty(messagePublishSystemTxtMessage)) {
			return null;
		}
		CodeSuccessResult messagePublishSystemResult = rongCloud.message.PublishSystem(userId, messagePublishSystemToUserId, messagePublishSystemTxtMessage, "thisisapush", "{\"pushData\":\"hello\"}", 0, 0);
		System.out.println("PublishSystem:  " + messagePublishSystemResult.toString());
		return messagePublishSystemResult;
	}
	
	// 发送系统模板消息方法（一个用户向一个或多个用户发送系统消息，单条消息最大 128k，会话类型为 SYSTEM.每秒钟最多发送 100 条消息，每次最多同时向 100 人发送，如：一次发送 100 人时，示为 100 条消息。） 
	public static CodeSuccessResult publishSystemTemplate(){
		CodeSuccessResult messagePublishSystemTemplateResult=null;
		try {
			reader = new InputStreamReader(new FileInputStream(JSONFILE+"TemplateMessage.json"));
			TemplateMessage publishSystemTemplateTemplateMessage  =  (TemplateMessage)GsonUtil.fromJson(reader, TemplateMessage.class);
			messagePublishSystemTemplateResult = rongCloud.message.publishSystemTemplate(publishSystemTemplateTemplateMessage);
			System.out.println("publishSystemTemplate:  " + messagePublishSystemTemplateResult.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(null != reader){
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return  messagePublishSystemTemplateResult;
	}
	
	// 发送群组消息方法（以一个用户身份向群组发送消息，单条消息最大 128k.每秒钟最多发送 20 条消息，每次最多向 3 个群组发送，如：一次向 3 个群组发送消息，示为 3 条消息。） 
	public static CodeSuccessResult publishGroup(String userId,String[] messagePublishGroupToGroupId,TxtMessage messagePublishGroupTxtMessage) throws Exception{
		if (StringUtils.isEmpty(userId)||messagePublishGroupToGroupId.length>0||StringUtils.isEmpty(messagePublishGroupTxtMessage)) {
			return null;
		}
		CodeSuccessResult messagePublishGroupResult = rongCloud.message.publishGroup(userId, messagePublishGroupToGroupId, messagePublishGroupTxtMessage, "thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
		System.out.println("publishGroup:  " + messagePublishGroupResult.toString());
		return messagePublishGroupResult;
	}
	
	// 发送讨论组消息方法（以一个用户身份向讨论组发送消息，单条消息最大 128k，每秒钟最多发送 20 条消息.） 
	public static CodeSuccessResult publishDiscussion(String userId,String discussionId,TxtMessage messagePublishDiscussionTxtMessage) throws Exception{
		if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(discussionId)||StringUtils.isEmpty(messagePublishDiscussionTxtMessage)) {
			return null;
		}
		CodeSuccessResult messagePublishDiscussionResult = rongCloud.message.publishDiscussion(userId, discussionId, messagePublishDiscussionTxtMessage, "thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
		System.out.println("publishDiscussion:  " + messagePublishDiscussionResult.toString());
		return messagePublishDiscussionResult;
	}
	
	// 发送聊天室消息方法（一个用户向聊天室发送消息，单条消息最大 128k。每秒钟限 100 次。） 
	public static CodeSuccessResult publishChatroom(String userId,String[] messagePublishChatroomToChatroomId,TxtMessage messagePublishChatroomTxtMessage ) throws Exception{
		if (StringUtils.isEmpty(userId)||messagePublishChatroomToChatroomId.length>0||StringUtils.isEmpty(messagePublishChatroomTxtMessage)) {
			return null;
		}
		CodeSuccessResult messagePublishChatroomResult = rongCloud.message.publishChatroom(userId, messagePublishChatroomToChatroomId, messagePublishChatroomTxtMessage);
		System.out.println("publishChatroom:  " + messagePublishChatroomResult.toString());
		return messagePublishChatroomResult;
	}
			
	// 发送广播消息方法（发送消息给一个应用下的所有注册用户，如用户未在线会对满足条件（绑定手机终端）的用户发送 Push 信息，单条消息最大 128k，会话类型为 SYSTEM。每小时只能发送 1 次，每天最多发送 3 次。） 
	public static CodeSuccessResult bobroadcast(String userId,TxtMessage messageBroadcastTxtMessage) throws Exception{
		if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(messageBroadcastTxtMessage)) {
			return null;
		}
		CodeSuccessResult messageBroadcastResult = rongCloud.message.broadcast(userId, messageBroadcastTxtMessage, "thisisapush", "{\"pushData\":\"hello\"}", "iOS");
		System.out.println("broadcast:  " + messageBroadcastResult.toString());
		return messageBroadcastResult;
	}

	// 消息历史记录下载地址获取 方法消息历史记录下载地址获取方法。获取 APP 内指定某天某小时内的所有会话消息记录的下载地址。（目前支持二人会话、讨论组、群组、聊天室、客服、系统通知消息历史记录下载） 
	public static model.rongCloud.HistoryMessageResult getHistory(String time) throws Exception{
		if (StringUtils.isEmpty(time)) {
			return null;
		}
		HistoryMessageResult messageGetHistoryResult = rongCloud.message.getHistory(time);
		System.out.println("getHistory:  " + messageGetHistoryResult.toString());
		return messageGetHistoryResult;
	}
	
	// 消息历史记录删除方法（删除 APP 内指定某天某小时内的所有会话消息记录。调用该接口返回成功后，date参数指定的某小时的消息记录文件将在随后的5-10分钟内被永久删除。） 
	public static CodeSuccessResult 	deleteMessage(String time) throws Exception{
		if (StringUtils.isEmpty(time)) {
			return null;
		}
		CodeSuccessResult messageDeleteMessageResult = rongCloud.message.deleteMessage("2014010101");
		System.out.println("deleteMessage:  " + messageDeleteMessageResult.toString());
		return messageDeleteMessageResult;
	}		
	
	//************************Wordfilter********************
	// 添加敏感词方法（设置敏感词后，App 中用户不会收到含有敏感词的消息内容，默认最多设置 50 个敏感词。） 
	public static CodeSuccessResult add(String wordfilter) throws Exception{
		if (StringUtils.isEmpty(wordfilter)) {
			return null;
		}
		CodeSuccessResult wordfilterAddResult = rongCloud.wordfilter.add(wordfilter);
		System.out.println("add:  " + wordfilterAddResult.toString());
		return wordfilterAddResult;
	}
	
	// 查询敏感词列表方法 
	public static ListWordfilterResult getList() throws Exception{
		ListWordfilterResult wordfilterGetListResult = rongCloud.wordfilter.getList();
		System.out.println("getList:  " + wordfilterGetListResult.toString());
		return wordfilterGetListResult;
	}
	
	// 移除敏感词方法（从敏感词列表中，移除某一敏感词。）
	public static CodeSuccessResult delete(String wordfilter) throws Exception{
		if (StringUtils.isEmpty(wordfilter)) {
			return null;
		}
		CodeSuccessResult wordfilterDeleteResult = rongCloud.wordfilter.delete(wordfilter);
		System.out.println("delete:  " + wordfilterDeleteResult.toString());
		return wordfilterDeleteResult;
	}
	
	//************************Group********************
	// 创建群组方法（创建群组，并将用户加入该群组，用户将可以收到该群的消息，同一用户最多可加入 500 个群，每个群最大至 3000 人，App 内的群组数量没有限制.注：其实本方法是加入群组方法 /group/join 的别名。） 
	public static CodeSuccessResult create(String[] groupCreateUserId,String groupId,String groupName) throws Exception{
		if (groupCreateUserId.length<=0||StringUtils.isEmpty(groupId)||StringUtils.isEmpty(groupName)) {
			return null;
		}
		CodeSuccessResult groupCreateResult = rongCloud.group.create(groupCreateUserId, groupId, groupName);
		System.out.println("create:  " + groupCreateResult.toString());
		return groupCreateResult;
	}
	// 同步用户所属群组方法（当第一次连接融云服务器时，需要向融云服务器提交 userId 对应的用户当前所加入的所有群组，此接口主要为防止应用中用户群信息同融云已知的用户所属群信息不同步。） 
	public static CodeSuccessResult sync(String userId,GroupInfo[] groupSyncGroupInfo) throws Exception{
		if (StringUtils.isEmpty(userId)||groupSyncGroupInfo.length<=0) {
			return null;
		}
		CodeSuccessResult groupSyncResult = rongCloud.group.sync(userId, groupSyncGroupInfo);
		System.out.println("sync:  " + groupSyncResult.toString());
		return groupSyncResult;
	}
	// 刷新群组信息方法 
	public static CodeSuccessResult refreshGroup(String groupId,String newGroupName) throws Exception{
		if (StringUtils.isEmpty(newGroupName)||StringUtils.isEmpty(groupId)) {
			return null;
		}
		CodeSuccessResult groupRefreshResult = rongCloud.group.refresh(groupId, newGroupName);
		System.out.println("refresh:  " + groupRefreshResult.toString());
		return groupRefreshResult;
	}
				
	// 将用户加入指定群组，用户将可以收到该群的消息，同一用户最多可加入 500 个群，每个群最大至 3000 人。 
	public static CodeSuccessResult join(String[] groupJoinUserId,String groupId,String groupName) {
		if (groupJoinUserId.length<=0||StringUtils.isEmpty(groupId)||StringUtils.isEmpty(groupName)) {
			return null;
		}
		CodeSuccessResult groupJoinResult = null;
		try {
			groupJoinResult = rongCloud.group.join(groupJoinUserId, groupId, groupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("join:  " + groupJoinResult.toString());
		return groupJoinResult;
	}
				
	// 查询群成员方法 
	public static GroupUserQueryResult queryUser(String groupId) throws Exception{
		if (StringUtils.isEmpty(groupId)) {
			return null;
		}
		GroupUserQueryResult groupQueryUserResult = rongCloud.group.queryUser(groupId);
		System.out.println("queryUser:  " + groupQueryUserResult.toString());
		return groupQueryUserResult;
	}
				
	// 退出群组方法（将用户从群中移除，不再接收该群组的消息.） 
	public static CodeSuccessResult quit(String[] groupQuitUserId,String groupName) throws Exception{
		if (groupQuitUserId.length<=0||StringUtils.isEmpty(groupName)) {
			return null;
		}
		CodeSuccessResult groupQuitResult = rongCloud.group.quit(groupQuitUserId, groupName);
		System.out.println("quit:  " + groupQuitResult.toString());
		return groupQuitResult;
	}
				
	// 添加禁言群成员方法（在 App 中如果不想让某一用户在群中发言时，可将此用户在群组中禁言，被禁言用户可以接收查看群组中用户聊天信息，但不能发送消息。） 
	public static CodeSuccessResult addGagUser(String userId,String groupId,String time) throws Exception{
		if (StringUtils.isEmpty(time)||StringUtils.isEmpty(groupId)||StringUtils.isEmpty(userId)) {
			return null;
		}
		CodeSuccessResult groupAddGagUserResult = rongCloud.group.addGagUser(userId, groupId, time);
		System.out.println("addGagUser:  " + groupAddGagUserResult.toString());
		return groupAddGagUserResult;
	}
	
	// 查询被禁言群成员方法 
	public static ListGagGroupUserResult lisGagUser(String groupId) throws Exception{
		if (StringUtils.isEmpty(groupId)) {
			return null;
		}
		ListGagGroupUserResult groupLisGagUserResult = rongCloud.group.lisGagUser(groupId);
		System.out.println("lisGagUser:  " + groupLisGagUserResult.toString());
		return groupLisGagUserResult;
	}
				
	// 移除禁言群成员方法 
	public static CodeSuccessResult rollBackGagUser(String[] groupRollBackGagUserUserId,String groupId) throws Exception{
		if (groupRollBackGagUserUserId.length<=0||StringUtils.isEmpty(groupId)) {
			return null;
		}
		CodeSuccessResult groupRollBackGagUserResult = rongCloud.group.rollBackGagUser(groupRollBackGagUserUserId, groupId);
		System.out.println("rollBackGagUser:  " + groupRollBackGagUserResult.toString());
		return groupRollBackGagUserResult;
	}
				
	// 解散群组方法。（将该群解散，所有用户都无法再接收该群的消息。）
	public static CodeSuccessResult dismiss(String userId,String groupId) throws Exception{
		if (StringUtils.isEmpty(groupId)||StringUtils.isEmpty(userId)) {
			return null;
		}
		CodeSuccessResult groupDismissResult = rongCloud.group.dismiss("userId1", "groupId1");
		System.out.println("dismiss:  " + groupDismissResult.toString());
		return groupDismissResult;
	}
				
			
}
