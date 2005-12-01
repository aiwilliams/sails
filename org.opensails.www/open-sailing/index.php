<?php get_header(); ?>

		<div id="Content">
			<div class="RightColumn">
				
					<?php if (have_posts()) : ?>
					<?php while (have_posts()) : the_post(); ?>
				
				<div class="Post" id="post-<?php the_ID(); ?>">
    				<h3 class="PostTitle"><a href="<?php the_permalink() ?>" rel="bookmark" title="Permanent Link to <?php the_title(); ?>"><?php the_title(); ?></a></h3>
    				<div class="PostInfo"><?php the_time('F jS, Y') ?>. <!-- by <?php the_author() ?> --><span class="PostComments">Posted in <?php the_category(', ') ?> <strong>|</strong> <?php edit_post_link('Edit','','<strong>|</strong>'); ?>  <?php comments_popup_link('No Comments &#187;', '1 Comment &#187;', '% Comments &#187;'); ?></span></div>
    				<span class="PostBody">
    					<?php the_content('Read the rest of this entry &raquo;'); ?>
    				</span>
    			</div>

				<?php endwhile; ?>
		
				<div class="navigation">
					<div class="alignleft"><?php previous_post_link('&laquo; %link') ?></div>
					<div class="alignright"><?php next_post_link('%link &raquo;') ?></div>
				</div>	
				<br />
				
				<?php else : ?>
		
				<h2 class="center">Not Found</h2>
				<p class="center">Sorry, but you are looking for something that isn't here.</p>
				<?php include (TEMPLATEPATH . "/searchform.php"); ?>
		
				<?php endif; ?>
				
			</div>

<?php get_sidebar(); ?>

<?php get_footer(); ?>







