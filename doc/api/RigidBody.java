interface RigidBody{

    List<double> myBounds;
    List<double> myPosition;
    List<int> myQuadrant;
    boolean isStatic;
    boolean isColliding(List<GameElement>);

}