/*
 * This file is part of the Aion-Emu project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package system.handlers.quest.event_quests;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.events.EventsService;

public class _80009TheCakeIsTheTruth extends QuestHandler
{
	
	private static final int questId = 80009;
	
	public _80009TheCakeIsTheTruth()
	{
		super(questId);
	}
	
	@Override
	public void register()
	{
		qe.registerQuestNpc(798417).addOnTalkEvent(questId);
		qe.registerOnLevelUp(questId);
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env)
	{
		final Player player = env.getPlayer();
		
		if (env.getTargetId() == 0)
		{
			return sendQuestStartDialog(env);
		}
		
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null)
		{
			return false;
		}
		
		final int var = qs.getQuestVarById(0);
		
		if (qs.getStatus() == QuestStatus.START)
		{
			if (env.getTargetId() == 798417)
			{
				switch (env.getDialog())
				{
					case START_DIALOG:
					{
						if (var == 0)
						{
							return sendQuestDialog(env, 2375);
						}
					}
					case SELECT_REWARD:
					{
						removeQuestItem(env, 182214007, 1);
						return defaultCloseDialog(env, 0, 1, true, true);
					}
				}
			}
		}
		return sendQuestRewardDialog(env, 798417, 0);
	}
	
	@Override
	public boolean onLvlUpEvent(QuestEnv env)
	{
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (!EventsService.getInstance().checkQuestIsActive(questId) && (qs != null))
		{
			QuestService.abandonQuest(player, questId);
		}
		return true;
	}
}
