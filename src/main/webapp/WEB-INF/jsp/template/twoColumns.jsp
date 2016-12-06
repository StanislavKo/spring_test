<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<div id="content">
	<tiles:insertAttribute name="content" />
</div>
<div id="side">
	<tiles:insertAttribute name="side" />
</div>
