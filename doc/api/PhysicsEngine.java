interface PhysicsEngine{

    void start();
    void stop();
    void add(RigidBody rb);
    void remove(RigidBody rb);
    void contains(RigidBody rb);
    void clear();
    void calcForces(PhysicsCalculator cl, List<Elements>);

}