<#include "inc/common.ftl">
<#include "inc/global-functions.ftl">

<head>
<title>${rc.getMessage("feedback.title")}</title>
<@cssAsset fileName="vascan" version=currentVersion! useMinified=false/>
</head>

<#assign currentPage="feedback"/>
<#include "inc/menu.ftl">
<h1>${rc.getMessage("feedback.title")}</h1>
<p>
${rc.getMessage("feedback.content_p1", ["<a href=\"https://github.com\">GitHub</a>"])}
</p>
<p>
<#assign feedbackGuidelineTitle=rc.getMessage("feedback.guideline.title")/>
<#assign hereWord = rc.getMessage("feedback.content.here")/>
${rc.getMessage("feedback.content_p2", ["<a href=\"https://github.com/join\">"+hereWord+"</a>", "<a href=\"https://github.com/Canadensys/vascan-data/blob/master/CONTRIBUTING.md\">" + feedbackGuidelineTitle + "</a>"])}
</p>
<p>
<ul>
	<li>${rc.getMessage("feedback.submit")} <a href="https://github.com/Canadensys/vascan-data/issues/new">${rc.getMessage("feedback.data_issue")}</a></li>
	<li>${rc.getMessage("feedback.submit")} <a href="https://github.com/Canadensys/vascan/issues/new">${rc.getMessage("feedback.functionality_issue")}</a></li>
</ul>
</p>
  </div><#-- content -->
</div>