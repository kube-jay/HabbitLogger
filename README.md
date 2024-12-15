At the Initial commit, there were lots of repitition, I have tried to minimize some of them 
This is a HabbitLogger that runs on the console
It connect to PostgreSql on my local computer as the host
I made a the connection to the database once, instead of repeating connections in every function as it was in the initial state
I made use of the PreparedStatement instead of just Statement, to improve security and avoid sql injections
