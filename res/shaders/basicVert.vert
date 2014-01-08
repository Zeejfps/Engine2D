attribute vec2 vertPos;
attribute vec2 vertexUV;

out vec2 UV;

void main() 
{

    gl_Position = vec4(vertPos, 0.0, 1.0);
    UV = vertexUV;

}