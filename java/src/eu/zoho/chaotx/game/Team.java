// TODO
public class Team{
    
    private short teamMembers;

    public Team(int n){
        if(n>=2)
            this.teamMembers = n;
        else
            this.teamMembers = 2;
    }
}