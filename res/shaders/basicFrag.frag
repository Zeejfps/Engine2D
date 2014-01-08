#version 330 core

in vec2 UV;

out vec3 color;

uniform sampler2d textureSampler;

void main()
{
	color = texture(textureSampler, UV).rgb;
}