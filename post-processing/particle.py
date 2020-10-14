from math import sqrt

class Particle():
    def __init__(self, id, xPosition, yPosition, xVelocity, yVelocity, radius, mass, animationRadius, redColor, greenColor, blueColor, timePassed):
        self.id=id
        self.x_position=xPosition
        self.y_position=yPosition
        self.x_velocity=xVelocity
        self.y_velocity=yVelocity
        self.radius=radius
        self.mass=mass 
        self.animation_radius=animationRadius 
        self.red_color=redColor
        self.green_color=greenColor
        self.blue_color=blueColor
        self.time_passed=timePassed

    def get_x_position(self):
        return self.x_position

    def get_y_position(self):
        return self.y_position

    def get_x_velocity(self):
        return self.x_velocity

    def get_y_velocity(self):
        return self.y_velocity

    def get_velocity(self):
        return sqrt((self.get_x_velocity() ** 2) + (self.get_y_velocity()**2))

    def get_time_passed(self):
        return self.time_passed          