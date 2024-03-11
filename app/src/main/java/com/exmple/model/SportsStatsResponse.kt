package com.exmple.model

import com.google.gson.annotations.SerializedName

data class SportsResponse(

	@field:SerializedName("matchUpStats")
	val matchUpStats: List<MatchUpStatsItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class VisStats(

	@field:SerializedName("timePoss")
	val timePoss: Int? = null,

	@field:SerializedName("rushYds")
	val rushYds: Int? = null,

	@field:SerializedName("fumblesLost")
	val fumblesLost: Int? = null,

	@field:SerializedName("statIdCode")
	val statIdCode: String? = null,

	@field:SerializedName("penalties")
	val penalties: Int? = null,

	@field:SerializedName("gameDate")
	val gameDate: String? = null,

	@field:SerializedName("fourthDownAtt")
	val fourthDownAtt: Int? = null,

	@field:SerializedName("teamCode")
	val teamCode: Int? = null,

	@field:SerializedName("firstDowns")
	val firstDowns: Int? = null,

	@field:SerializedName("thirdDownConver")
	val thirdDownConver: Int? = null,

	@field:SerializedName("interceptionsThrown")
	val interceptionsThrown: Int? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("passComp")
	val passComp: Int? = null,

	@field:SerializedName("thridDownAtt")
	val thridDownAtt: Int? = null,

	@field:SerializedName("fourthDownConver")
	val fourthDownConver: Int? = null,

	@field:SerializedName("passAtt")
	val passAtt: Int? = null,

	@field:SerializedName("gameCode")
	val gameCode: String? = null,

	@field:SerializedName("penaltYds")
	val penaltYds: Int? = null,

	@field:SerializedName("rushAtt")
	val rushAtt: Int? = null,

	@field:SerializedName("passYds")
	val passYds: Int? = null
)

data class MatchUpStatsItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("visStats")
	val visStats: VisStats? = null,

	@field:SerializedName("visTeamName")
	val visTeamName: String? = null,

	@field:SerializedName("homeStats")
	val homeStats: HomeStats? = null,

	@field:SerializedName("neutral")
	val neutral: Boolean? = null,

	@field:SerializedName("homeTeamName")
	val homeTeamName: String? = null,

	@field:SerializedName("isFinal")
	val isFinal: Boolean? = null
)

data class HomeStats(

	@field:SerializedName("timePoss")
	val timePoss: Int? = null,

	@field:SerializedName("rushYds")
	val rushYds: Int? = null,

	@field:SerializedName("fumblesLost")
	val fumblesLost: Int? = null,

	@field:SerializedName("statIdCode")
	val statIdCode: String? = null,

	@field:SerializedName("penalties")
	val penalties: Int? = null,

	@field:SerializedName("gameDate")
	val gameDate: String? = null,

	@field:SerializedName("fourthDownAtt")
	val fourthDownAtt: Int? = null,

	@field:SerializedName("teamCode")
	val teamCode: Int? = null,

	@field:SerializedName("firstDowns")
	val firstDowns: Int? = null,

	@field:SerializedName("thirdDownConver")
	val thirdDownConver: Int? = null,

	@field:SerializedName("interceptionsThrown")
	val interceptionsThrown: Int? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("passComp")
	val passComp: Int? = null,

	@field:SerializedName("thridDownAtt")
	val thridDownAtt: Int? = null,

	@field:SerializedName("fourthDownConver")
	val fourthDownConver: Int? = null,

	@field:SerializedName("passAtt")
	val passAtt: Int? = null,

	@field:SerializedName("gameCode")
	val gameCode: String? = null,

	@field:SerializedName("penaltYds")
	val penaltYds: Int? = null,

	@field:SerializedName("rushAtt")
	val rushAtt: Int? = null,

	@field:SerializedName("passYds")
	val passYds: Int? = null
)
