Exception in thread "Craft Scheduler Thread - 1"
[23:14:02 WARN]: org.apache.commons.lang.UnhandledException: Plugin BedwarsPractice v1.0.0 generated an exception while executing task 4
at org.bukkit.craftbukkit.v1_8_R3.scheduler.CraftAsyncTask.run(CraftAsyncTask.java:56)
at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
at java.lang.Thread.run(Unknown Source)
Caused by: java.util.ConcurrentModificationException
at java.util.HashMap$HashIterator.nextNode(Unknown Source)
at java.util.HashMap$ValueIterator.next(Unknown Source)
at it.thedarksword.bedwarspractice.utils.values.Numeric$Doubles.sum(Numeric.java:55)
at it.thedarksword.bedwarspractice.abstraction.sessions.bridging.BridgingSession.getMovementSpeed(BridgingSession.java:91)
at it.thedarksword.bedwarspractice.bridging.sessions.straight.none.ShortStraightBridging.sidebarTemplate(ShortStraightBridging.java:45)
at it.thedarksword.bedwarspractice.scoreboard.ScoreBoard.template(ScoreBoard.java:194)
at it.thedarksword.bedwarspractice.scoreboard.ScoreBoard.refresh(ScoreBoard.java:213)
at java.util.concurrent.ConcurrentHashMap$ValuesView.forEach(Unknown Source)
at it.thedarksword.bedwarspractice.scoreboard.BoardsHandler$SidebarRunnable.run(BoardsHandler.java:124)
at org.bukkit.craftbukkit.v1_8_R3.scheduler.CraftTask.run(CraftTask.java:71)
at org.bukkit.craftbukkit.v1_8_R3.scheduler.CraftAsyncTask.run(CraftAsyncTask.java:53)
... 3 more