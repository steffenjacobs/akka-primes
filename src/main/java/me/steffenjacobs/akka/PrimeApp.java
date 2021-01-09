package me.steffenjacobs.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class PrimeApp extends UntypedActor {
    @Override
    public void onReceive(Object o) {
        //Nothing to do
    }

    public static void main(String[] args) {
        //create the actor system and startup the PrimeMaster actor
        ActorSystem actorSystem = ActorSystem.create("system");
        ActorRef primeMaster = actorSystem.actorOf(Props.create(PrimeMaster.class), "master");

        //send the PrimeMaster actor the segment of numbers
        primeMaster.tell(new SegmentMessage(0, 100), actorSystem.actorOf(Props.create(PrimeApp.class), "app"));
    }
}
