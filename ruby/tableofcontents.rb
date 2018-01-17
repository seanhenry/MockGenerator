require 'mustache'
require 'json'

def get_items(dir)
  Dir.glob("#{dir}*.json").map do |file|
    {
        title: JSON.parse(File.read(file))['title'],
        link: file.sub(dir, '').sub('.json', '.md')
    }
  end
end

groups = Dir.foreach('examples')
            .sort
            .reject { |f| f == '.' || f == '..' }
            .select { |f| File.directory?("examples/#{f}") }
            .map { |dir| { title: dir, items: get_items("examples/#{dir}/") } }

class TableOfContents < Mustache
end

template = TableOfContents.new
template[:groups] = groups
File.write('../documentation/README.md', template.render)
