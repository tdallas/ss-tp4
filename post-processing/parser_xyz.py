from particle import Particle


class XYZParser:
    def __init__(self, output_path):
        self.output = self.parse_output(output_path)

    def get_output(self):
        return self.output

    def parse_output(self, output_path):
        output = []
        with open(output_path) as f:
            lines = f.readlines()
            iteration = []
            for line in lines:
                if self.is_header(line):
                    pass
                elif self.iteration_finished(line):
                    if len(iteration) > 1: 
                        output.append(iteration)
                    iteration = []
                else:
                    particle = self.create_particle(line.replace('\n', '').split(' '))
                    iteration.append(particle)
        return output

    def get_particle_with_id(self, id):
        particle_with_id = []
        for i in range(len(self.output)):
            for j in range(len(self.output[i])):
                if self.output[i][j].get_id() == id:
                    particle_with_id.append(self.output[i][j])
        return particle_with_id
    
    @staticmethod
    def is_header(line):
        return line == 'id xPosition yPosition xVelocity yVelocity radius mass animationRadius redColor greenColor blueColor timePassed\n'

    @staticmethod
    def iteration_finished(line):
        return len(line.split(' ')) == 1

    @staticmethod
    def create_particle(line):
        return Particle(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[11])
